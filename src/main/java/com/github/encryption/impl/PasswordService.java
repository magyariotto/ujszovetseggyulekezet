package com.github.encryption.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordService {
    private static final Logger log = LoggerFactory.getLogger(PasswordService.class);
    private static final String ID = "$31$";
    private static final int DEFAULT_COST = 16;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SIZE = 128;
    private static final Pattern layout = Pattern.compile("\\$31\\$(\\d\\d?)\\$(.{43})");
    private final SecureRandom random;
    private final int cost;

    public PasswordService() {
        this(16);
    }

    public PasswordService(int cost) {
        iterations(cost);
        this.cost = cost;
        this.random = new SecureRandom();
    }

    private static int iterations(int cost) {
        if (cost >= 0 && cost <= 30) {
            return 1 << cost;
        } else {
            throw new IllegalArgumentException("cost: " + cost);
        }
    }

    public String hashPassword(char[] password) {
        byte[] salt = new byte[16];
        this.random.nextBytes(salt);
        byte[] dk = pbkdf2(password, salt, 1 << this.cost);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return "$31$" + this.cost + '$' + enc.encodeToString(hash);
    }

    public boolean authenticate(char[] password, String token) {
        Matcher m = layout.matcher(token);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid token format");
        } else {
            int iterations = iterations(Integer.parseInt(m.group(1)));
            byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
            byte[] salt = Arrays.copyOfRange(hash, 0, 16);
            byte[] check = pbkdf2(password, salt, iterations);
            int zero = 0;

            for(int idx = 0; idx < check.length; ++idx) {
                zero |= hash[salt.length + idx] ^ check[idx];
            }

            return zero == 0;
        }
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, 128);

        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException var5) {
            throw new IllegalStateException("Missing algorithm: PBKDF2WithHmacSHA1", var5);
        } catch (InvalidKeySpecException var6) {
            throw new IllegalStateException("Invalid SecretKeyFactory", var6);
        }
    }

    public String hashPassword(String password) {
        return this.hashPassword(password.toCharArray());
    }

    public boolean authenticate(String password, String token) {
        return this.authenticate(password.toCharArray(), token);
    }
}

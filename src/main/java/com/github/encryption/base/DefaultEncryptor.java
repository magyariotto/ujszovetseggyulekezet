package com.github.encryption.base;

import org.codehaus.plexus.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class DefaultEncryptor {
    private static final Logger log = LoggerFactory.getLogger(DefaultEncryptor.class);
    private static final int SIZE = 16;
    private static final String ALGORITHM = "AES";
    private final Key key;
    private final Cipher cipher;

    public DefaultEncryptor(String password) {
        byte[] key = this.createKey(password);
        this.key = new SecretKeySpec(key, "AES");

        try {
            this.cipher = Cipher.getInstance("AES");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException var4) {
            log.error("Error creating encryptor.", var4);
            throw new RuntimeException(var4);
        }
    }

    private byte[] createKey(String password) {
        if (password.length() < 16) {
            int missingLength = 16 - password.length();
            StringBuilder passwordBuilder = new StringBuilder(password);

            for(int i = 0; i < missingLength; ++i) {
                passwordBuilder.append(" ");
            }

            password = passwordBuilder.toString();
        }

        return password.substring(0, 16).getBytes(StandardCharsets.UTF_8);
    }

    public String encrypt(String text) {
        try {
            this.cipher.init(1, this.key);
            byte[] encrypted = this.cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            byte[] base64 = Base64.encodeBase64(encrypted);
            return new String(base64, StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException var4) {
            log.error("Error encryping value.", var4);
            throw new RuntimeException(var4);
        }
    }

    public String decrypt(String text) {
        try {
            this.cipher.init(2, this.key);
            byte[] base64 = Base64.decodeBase64(text.getBytes(StandardCharsets.UTF_8));
            byte[] decrypted = this.cipher.doFinal(base64);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException var4) {
            log.error("Error decrypting value.", var4);
            throw new RuntimeException(var4);
        }
    }}

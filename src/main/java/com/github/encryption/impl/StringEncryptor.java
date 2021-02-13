package com.github.encryption.impl;

import com.github.encryption.base.DefaultEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringEncryptor {
    private static final Logger log = LoggerFactory.getLogger(StringEncryptor.class);

    protected String encryptEntity(String entity, String key) {
        DefaultEncryptor encryption = new DefaultEncryptor(key);
        return encryption.encrypt(entity);
    }

    protected String decryptEntity(String entity, String key) {
        DefaultEncryptor decryption = new DefaultEncryptor(key);
        return decryption.decrypt(entity);
    }

    public StringEncryptor() {
    }
}

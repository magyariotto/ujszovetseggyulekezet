package com.github.encryption.impl;

public class BooleanEncryptor {
    private final StringEncryptor stringEncryptor;

    protected String encrypt(Boolean entity, String key) {
        return this.stringEncryptor.encryptEntity(entity.toString(), key);
    }

    protected Boolean decrypt(String entity, String key) {
        return Boolean.parseBoolean(this.stringEncryptor.decryptEntity(entity, key));
    }

    public BooleanEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }
}

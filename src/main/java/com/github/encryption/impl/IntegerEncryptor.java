package com.github.encryption.impl;

import com.github.encryption.base.AbstractEncryptor;

public class IntegerEncryptor extends AbstractEncryptor<Integer> {
    private final StringEncryptor stringEncryptor;

    protected String encrypt(Integer entity, String key) {
        return this.stringEncryptor.encryptEntity(entity.toString(), key);
    }

    protected Integer decrypt(String entity, String key) {
        return Integer.valueOf((String)this.stringEncryptor.decryptEntity(entity, key));
    }

    public IntegerEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

}

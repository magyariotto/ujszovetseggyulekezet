package com.github.encryption.base;

public abstract class AbstractEncryptor<T> implements Encryptor<T> {
    public AbstractEncryptor() {
    }

    public String encryptEntity(T entity, String key) {
        this.validateKey(key);
        return entity == null ? null : this.encrypt(entity, key);
    }

    public T decryptEntity(String entity, String key) {
        this.validateKey(key);
        return entity == null ? null : this.decrypt(entity, key);
    }

    private void validateKey(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null.");
        }
    }

    protected abstract String encrypt(T var1, String var2);

    protected abstract T decrypt(String var1, String var2);
}

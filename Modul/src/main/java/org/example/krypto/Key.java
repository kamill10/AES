package org.example.krypto;

import java.security.SecureRandom;

public class Key {
    public byte[] keyGenerator(int size) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[size];
        secureRandom.nextBytes(key);
        return key;
    }
}


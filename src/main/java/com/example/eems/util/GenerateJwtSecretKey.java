package com.example.eems.util;

import java.security.Key;
import java.util.Base64;

import io.jsonwebtoken.security.Keys;

public class GenerateJwtSecretKey {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println("Base64 Encoded Secret Key: " + encodedKey);
    }
}
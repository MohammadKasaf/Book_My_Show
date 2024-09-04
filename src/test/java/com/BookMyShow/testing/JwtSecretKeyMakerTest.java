package com.BookMyShow.testing;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;


public class JwtSecretKeyMakerTest {

    // Test the method to generate a secret key
    @Test
    public void generateSecretKey(){

        // Generating a secret key using HS512 algorithm via Jwts
        SecretKey key=Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS512);

        // Converting the secret key to a hexadecimal string
        String encodedKey= DatatypeConverter.printHexBinary(key.getEncoded());

        // Printing the encoded key
        System.out.println("Encoded secret key: "+encodedKey);

    }

}

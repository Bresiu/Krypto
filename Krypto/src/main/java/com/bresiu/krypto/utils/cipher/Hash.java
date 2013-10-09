package com.bresiu.krypto.utils.cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Hash {
    public static String toHash(String credential) throws NoSuchAlgorithmException,
            NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        MessageDigest md = MessageDigest.getInstance("SHA-512", "BC");
        byte[] digestCredential = md.digest(credential.getBytes());

        return String.format("%0128x", new BigInteger(1, digestCredential));
    }
}

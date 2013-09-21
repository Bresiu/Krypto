package com.bresiu.krypto.utils;

//Dummy Caesar cipher for test
public class CaesarDecrypt {
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz .";
    private final int SHIFT = 6;

    public String CaesarDecrypt(String cipherText) {
        cipherText = cipherText.toLowerCase();
        String message = "";
        for (int i = 1; i < cipherText.length() - 1; i++) {
            int charPosition = this.ALPHABET.indexOf(cipherText.charAt(i));
            int keyVal = (charPosition - SHIFT) % 28;
            if (keyVal < 0) {
                keyVal = this.ALPHABET.length() + keyVal;
            }
            char replaceVal = this.ALPHABET.charAt(keyVal);
            message += replaceVal;
        }
        return message;
    }
}

package com.bresiu.krypto.utils.cipher;

//Dummy Caesar cipher for test
public class CaesarEncrypt {
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz .";
    private final int SHIFT = 6;

    public String caesarEncrypt(String message) {
        message = message.toLowerCase();
        String cipherText = "";
        for (int i = 0; i < message.length(); i++) {
            int charPosition = ALPHABET.indexOf(message.charAt(i));
            int keyVal = (SHIFT + charPosition) % 28;
            char replaceVal = this.ALPHABET.charAt(keyVal);
            cipherText += replaceVal;
        }
        return cipherText;
    }
}

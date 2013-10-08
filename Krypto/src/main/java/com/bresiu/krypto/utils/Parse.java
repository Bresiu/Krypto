package com.bresiu.krypto.utils;

public class Parse {
    public static String phoneNo(String phoneNo) {
        String newPhoneNo = "";
        int j = 0;
        for (int i = phoneNo.length() - 1; i >= 0; i--) {
            if ((i != (phoneNo.length() - 1)) && j % 3 == 0) {
                newPhoneNo += " " + phoneNo.charAt(j);
            } else {
                newPhoneNo += phoneNo.charAt(j);
            }
            j++;
        }

        return newPhoneNo;
    }
}

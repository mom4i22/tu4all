package com.momchil.TU4ALL.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Digest {
    private MessageDigest md5;
    private StringBuffer digestBuffer;

    public MD5Digest() throws NoSuchAlgorithmException {
        md5 = MessageDigest.getInstance("MD5");
        digestBuffer = new StringBuffer();
    }


    public String md5crypt(String password) {
        int index;
        byte[] digest;
        if (password == null) {
            return null;
        }
        digestBuffer.setLength(0);
        digest = md5.digest(password.getBytes());

        for (index = 0; index < digest.length; ++index) {
            digestBuffer.append(Integer.toHexString(digest[index] & 0xff));
        }

        return digestBuffer.toString();
    }

}
package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.util.Log;

public class VigenereCipher {

    protected static String encrypt_vg(String text, String key)
    {
        StringBuilder res = new StringBuilder();
        text = text.toUpperCase();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res .append((char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A'));
            Log.d("encrypt_vg: ", String.valueOf(j));
            j = ++j % key.length();
        }
        return res.toString();
    }

    protected static String decrypt_vg(String text, String key)
    {
        StringBuilder res = new StringBuilder();
        key = key.toUpperCase();
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res.append((char) ((c - key.charAt(j) + 26) % 26 + 'A'));
            j = ++j % key.length();
        }
        return res.toString();
    }
}

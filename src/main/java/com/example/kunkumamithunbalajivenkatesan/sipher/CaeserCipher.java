package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.util.Log;

public class CaeserCipher {
    private String dat;
    private int coc;
    public void CaserCipher(String mes, int con){
        this.dat = mes;
        this.coc = con;
    }

    public String decrypt(){
        StringBuilder decryptedMessage = new StringBuilder();

        char ch;
        for(int i = 0; i < this.dat.length(); ++i){
            ch = this.dat.charAt(i);
            if(ch >= 'a' && ch <= 'z'){
                ch = (char)(ch - this.coc);
                if(ch < 'a'){
                    ch = (char)(ch + 'z' - 'a' + 1);
                }
                decryptedMessage.append(ch);
            }
            else if(ch >= 'A' && ch <= 'Z'){
                ch = (char)(ch - this.coc);
                if(ch < 'A'){
                    ch = (char)(ch + 'Z' - 'A' + 1);
                }
                decryptedMessage.append(ch);
            }
            else {
                decryptedMessage.append(ch);
            }
        }
        return decryptedMessage.toString();
    }
    public String encrypt()
    {
        StringBuilder result= new StringBuilder();
        for (int i=0; i< this.dat.length(); i++)
        {
            if (Character.isUpperCase(this.dat.charAt(i)))
            {
                char ch = (char)(((int)this.dat.charAt(i) +
                        this.coc - 65) % 26 + 65);
                result.append(ch);

            }
            else if (Character.isLowerCase(this.dat.charAt(i)))
            {
                char ch = (char)(((int)this.dat.charAt(i) +
                        this.coc - 97) % 26 + 97);
                result.append(ch);

            }
            else if (Character.isWhitespace(this.dat.charAt(i))){
                result.append(" ");
            }
        }
        return result.toString();
    }
    public boolean isInteger() {
        try {
            final int i = this.coc;
            Log.d("Test", Integer.toString(i));
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

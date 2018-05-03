package com.example.kunkumamithunbalajivenkatesan.sipher;

public class OneTimePad {
    private int returnNumber(char message){
        int ord = 0;
        switch (message){
            case 'a':ord =  1;break;
            case 'b':ord =  2;break;
            case 'c':ord =  3;break;
            case 'd':ord =  4;break;
            case 'e':ord =  5;break;
            case 'f':ord =  6;break;
            case 'g':ord =  7;break;
            case 'h':ord =  8;break;
            case 'i':ord =  9;break;
            case 'j':ord =  10;break;
            case 'k':ord =  11;break;
            case 'l':ord =  12;break;
            case 'm':ord =  13;break;
            case 'n':ord =  14;break;
            case 'o':ord =  15;break;
            case 'p':ord =  16;break;
            case 'q':ord =  17;break;
            case 'r':ord =  18;break;
            case 's':ord =  19;break;
            case 't':ord =  20;break;
            case 'u':ord =  21;break;
            case 'v':ord =  22;break;
            case 'w':ord =  23;break;
            case 'x':ord =  24;break;
            case 'y':ord =  25;break;
            case 'z':ord =  26;break;
        }

        return ord;
    }

    private int returnChar(int message){
        char ord;
        switch (message){
            case 1:ord = 'a';break;
            case 2:ord = 'b';break;
            case 3:ord = 'c';break;
            case 4:ord = 'd';break;
            case 5:ord = 'e';break;
            case 6:ord = 'f';break;
            case 7:ord = 'g';break;
            case 8:ord = 'h';break;
            case 9:ord = 'i';break;
            case 10:ord = 'j';break;
            case 11:ord = 'k';break;
            case 12:ord = 'l';break;
            case 13:ord = 'm';break;
            case 14:ord = 'n';break;
            case 15:ord = 'o';break;
            case 16:ord = 'p';break;
            case 17:ord = 'q';break;
            case 18:ord = 'r';break;
            case 19:ord = 's';break;
            case 20:ord = 't';break;
            case 21:ord = 'u';break;
            case 22:ord = 'v';break;
            case 23:ord = 'w';break;
            case 24:ord = 'x';break;
            case 25:ord = 'y';break;
            case 26:ord = 'z';break;
            default:ord = (char)(message+97);break;

        }
        return ord;
    }

    private int mod26(int sum){
        if (sum%26 != 0){
            if (sum<0) sum = 26 + sum;
            return (sum % 26);
        }
        else {
            return sum;
        }

    }
    protected String encrypt_otp(String message, String pass){
        message = message.toLowerCase();
        pass = pass.toLowerCase();
        StringBuilder enc_mes = new StringBuilder();
        for(int i = 0; i<message.length(); i++){
            char a = (char) returnChar(mod26(returnNumber(message.charAt(i))+returnNumber(pass.charAt(i))));
            enc_mes.append(a);
        }
        return enc_mes.toString();
    }

    protected String decrypt_otp(String message, String pass){
        StringBuilder enc_mes = new StringBuilder();
        for(int i = 0; i<message.length(); i++){
            char a = (char) returnChar(mod26(returnNumber(message.charAt(i))-returnNumber(pass.charAt(i))));
            enc_mes.append(a);
        }
        return enc_mes.toString();
    }
}

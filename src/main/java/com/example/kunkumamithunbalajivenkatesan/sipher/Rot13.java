package com.example.kunkumamithunbalajivenkatesan.sipher;

public class Rot13 {
    private String message;

    public void rot13(String message){
        this.message = message;
    }
    public String rot(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.message.length(); i++) {
            char c = this.message.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }
        return sb.toString();
    }
}

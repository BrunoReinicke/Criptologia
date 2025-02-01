/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package criptologia;

import java.util.ArrayList;

/**
 * @author Bruno Reinicke
 */
public class Cifragem {
    
    public String encryptAfim(String senha) {
        senha = this.encrypt(senha);
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÞÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®­¬«ª©¨§¦¥¤£¡";
        String cifra = "";
        ArrayList<Integer> aux = new ArrayList<>();
        
        for (int i = 0; i < senha.length(); i++) 
            aux.add(crypto.indexOf(senha.charAt(i)));
        for (int j = 0; j < aux.size(); j++) 
            cifra += crypto.charAt((12 * aux.get(j) + 14) % crypto.length()); 
        return cifra;
    }
    
    public String decryptAfim(String cifra) {
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÞÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®­¬«ª©¨§¦¥¤£¡";
        String senha = "";
        int inv = 0;
        int length = crypto.length();
        
        for (int x = 1; x < crypto.length(); x++) 
            if ((12 * x) % crypto.length() == 1) 
                inv = x;

        for (int i = 0; i < cifra.length(); i++) {
            int indice = crypto.indexOf(cifra.charAt(i));
            if (indice == -1) continue;
            int  y = (inv * ((indice - 14 + length) % length)) % length;
            senha += crypto.charAt(y) + "";
        }
        return this.decrypt(senha);
    }
    
    public String encrypt(String senha) {
        String caract = "!#$%&'()*+,-./0123456789:;<=>?@abcdefghijklmnopqrstuvwxyz[]^_`ABCDEFGHIJKLMNOPQRSTUVWXYZ\\";
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÞÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®­¬«ª©¨§¦¥¤£¡"; 
        String cipher = "";
        
        for (int z = 0; z < senha.length(); z++) 
            for (int x = 0; x < caract.length(); x++) {
                if ((caract.charAt(x)+"").equals(senha.charAt(z)+"")) {
                    cipher = cipher + crypto.charAt(x); 
                    break;
                }
                if (senha.length() == cipher.length())
                    break;
            }
        return this.invOrdem(cipher);
    }
    
    public String decrypt(String cipher) {
        String caract = "!#$%&'()*+,-./0123456789:;<=>?@abcdefghijklmnopqrstuvwxyz[]^_`ABCDEFGHIJKLMNOPQRSTUVWXYZ\\";
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÞÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®­¬«ª©¨§¦¥¤£¡";
        String senha  = "";
 
        for (int z = 0; z < cipher.length(); z++) 
            for (int x = 0; x < crypto.length(); x++) {
                if ((crypto.charAt(x)+"").equals(cipher.charAt(z)+"")) {
                    senha += caract.charAt(x); 
                    break;
                }
                if (senha.length() == cipher.length())
                    break;
            }
        return this.invOrdem(senha);       
    }
    
    public String invOrdemAux(String senha) {
        String s = "";
        for (int i = senha.length() - 1; i >= 0; i--)
            s += senha.charAt(i);
        return s;
    }
    
    public String invOrdem(String senha) {
        int i = senha.length() / 2;
        if (Double.valueOf(senha.length() % 2) == 0) 
            return this.invOrdemAux(
                senha.substring(i,senha.length()) +
                senha.substring(0,i));
        else
            return this.invOrdemAux(
                senha.substring(i+1,senha.length()) +
                senha.charAt(i) +
                senha.substring(0,i));
    }
}
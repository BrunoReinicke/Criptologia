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
    
    public String encryptAfim(String crypto, int index) {
        return String.valueOf(crypto.charAt((12 * index + 14) % crypto.length()));
    }
    
    public String getCifra(ArrayList<ArrayList<Integer>> blocos, String crypto, String senha) {
        // Determinante = 1 * (14 * 17 - 15 * 16) - 11 * (13 * 17 - 15 * 2) + 12 * (13 * 16 - 14 * 2) 
        int matriz[][] = {{1, 11, 12},
                          {13, 14, 15},
                          {2, 16, 17}}; 
        int length = crypto.length();
        String cifra = "";
        int strFin = (blocos.size() * 3) + (senha.length() - (blocos.size() * 3)) - 1;
        
        if (blocos.size() * 3 < senha.length()) {
           ArrayList<Integer> bloco = new ArrayList<>();
            for (int z = strFin; z < senha.length(); z++) 
                bloco.add(crypto.indexOf(senha.charAt(z)));
            if (bloco.size() < 3) 
                while (bloco.size() < 3)
                    bloco.add(26);
            blocos.add(bloco);           
        }
        
        for (int x = 0; x < blocos.size(); x++) {
            ArrayList<Integer> bloco  = blocos.get(x);        
            for (int[] matriz1 : matriz) {
                int index = 0;
                for (int y = 0; y < bloco.size(); y++) 
                    index += bloco.get(y) * matriz1[y];
                cifra += crypto.charAt(((index % length) + length) % length);
            }    
        }
        return cifra;
    }
    
    public String getSenha(ArrayList<ArrayList<Integer>> blocos, String crypto, String cifra) {
        /* Fórmula cálculo de cofatores:
            int K[][] = {{1, 2, 3},
                         {4, 5, 6},
                         {7, 8, 10}};
            Cof(K)=   
                +(5⋅10−6⋅8) −(4⋅10−6⋅7) +(4⋅8−5⋅7)
                −(2⋅10−3⋅8) +(1⋅10−3⋅7) −(1⋅8−2⋅7)
                +(2⋅6−3⋅5)  −(1⋅6−3⋅4)  +(1⋅5−2⋅4)*/
        
       /* int matriz[][] = {{1, 11, 12},
                          {13, 14, 15},
                          {2, 16, 17}}; 
        int cofatores[][] = {{-2, -191, 180},
                             {5, -7, 6},
                             {-3, 141, -129}};*/
        int adj[][] = {{-2,    5,   -3},
                       {-191, -7,  141},
                       {180,   6, -129}};
        int length = crypto.length();
        String senha = "";
  
        for (int x = 0; x < blocos.size(); x++) {
            ArrayList<Integer> bloco  = blocos.get(x);        
            for (int[] adj1 : adj) {
                int index = 0;
                for (int y = 0; y < bloco.size(); y++) 
                    index += bloco.get(y) * adj1[y];
                senha += crypto.charAt(((index % length) + length) % length);
            }    
        }
        return senha;
    }
    
    public static int calcularMDC(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    public static int calcInvMod(int a, int b) {
        int inv = 1;
        int resto = 0;
        while (resto != 1) {
            resto = (a * inv) % b;
            inv++;
        }
        return (inv - 1);
    }
    
    public static int restoDivMod(int a, int b) {
        int resto = 0;
        int mult = 1;
        if (a > 0) {
            while ((mult * b) < a) {
                resto = a - (mult * b);
                mult++;
            }
        } else {
            while ((a + (b * mult)) <= 0) 
                mult++;
            resto = mult;
        }
        return resto;
    }

    public String encryptHill(String senha) {
        senha = this.encrypt(senha);
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÞÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®­¬«ª©¨§¦¥¤£¡";
        String cifra = "";
        ArrayList<Integer> bloco = new ArrayList<>();
        ArrayList<ArrayList<Integer>> blocos = new ArrayList<>();
        int length = crypto.length();
        int count  = 1;
        
        for (int i = 0; i < senha.length(); i++) {
            bloco.add(crypto.indexOf(senha.charAt(i)));
            if (count == 3) {
                blocos.add(bloco);
                bloco = new ArrayList<>();
                count = 0;
            }
            count++;
        }
        return this.getCifra(blocos, crypto, senha);
    }
    
    public String decryptHill(String cifra) {         
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÞÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®­¬«ª©¨§¦¥¤£¡";
        ArrayList<Integer> bloco = new ArrayList<>();
        ArrayList<ArrayList<Integer>> blocos = new ArrayList<>();
        int length = crypto.length();
        int count  = 1;
        
        for (int i = 0; i < cifra.length(); i++) {
            bloco.add(crypto.indexOf(cifra.charAt(i)));
            if (count == 3) {
                blocos.add(bloco);
                bloco = new ArrayList<>();
                count = 0;
            }
            count++;
        }
        return this.decrypt(this.getSenha(blocos, crypto, cifra));
    }
    
    private String decryptAfim(String caract, int index) {
        int inv = 0;
        int length = caract.length();
        
        for (int x = length; x >= 0; x--) 
            if ((12 * x) % length == 1) { 
                inv = x;
                break;
            }
        return String.valueOf(caract.charAt((inv * ((index - 14 + length) % length)) % length));
    }
    
    public String encrypt(String senha) {
        String caract = "!#$%&'()*+,-./0123456789:;<=>?@abcdefghijklmnopqrstuvwxyz[]^_`ABCDEFGHIJKLMNOPQRSTUVWXYZ\\";
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÞÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®­¬«ª©¨§¦¥¤£¡"; 
        String cipher = "";
        
        for (int z = 0; z < senha.length(); z++) 
            for (int x = 0; x < caract.length(); x++) {
                if ((caract.charAt(x)+"").equals(senha.charAt(z)+"")) {
                    cipher += this.encryptAfim(crypto, crypto.indexOf(crypto.charAt(x))); 
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
                    senha += this.decryptAfim(caract, caract.indexOf(caract.charAt(x))); 
                    break;
                }
                if (senha.length() == cipher.length())
                    break;
            }
        return this.invOrdem(senha);       
    }
    
    private String invOrdemAux(String senha) {
        String s = "";
        for (int i = senha.length() - 1; i >= 0; i--)
            s += senha.charAt(i);
        return s;
    }
    
    private String invOrdem(String senha) {
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
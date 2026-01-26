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
        return String.valueOf(crypto.charAt((18 * index + 20) % (crypto.length() - 1)));
    }

    public String getCifraDinamica(ArrayList<ArrayList<Integer>> blocos, String crypto, String senha) {
        int matriz[][] = {{18, 11, 12},
                          {13, 14, 15},
                          {2, 16, 17}}; 
        int reserva[][] = {{19, 12, 13},
                           {14, 15, 16},
                           {3, 17, 18}}; 
        int auxiliar[][];  
        int anterior[][] = new int[3][3];
        int length = crypto.length();
        String cifra = "";
        int strFin = (blocos.size() * 3);
        int count = 0;
        
        if (blocos.size() * 3 < senha.length()) {
            ArrayList<Integer> bloco = new ArrayList<>();
            for (int z = strFin; z < senha.length(); z++) 
                bloco.add(crypto.indexOf(senha.charAt(z)));
            if (bloco.size() < 3) 
                while (bloco.size() < 3)
                    bloco.add(89);
            blocos.add(bloco);           
        }
        
        int coprimo = 1;
        auxiliar = matriz;
        for (int x = 0; x < blocos.size(); x++) {
            if (count == 3) {
                auxiliar = matriz;
                count = 0;
            }
            if (count == 2) {
                auxiliar = this.getMatrPermuta(anterior); 
                if (getGcd(this.getDeterminante(auxiliar),length) != 1) {
                    auxiliar = this.somaMatrIdent(auxiliar);
                    if (getGcd(this.getDeterminante(auxiliar),length) != 1) {
                        auxiliar = reserva;
                        count++;
                    }
                }
            }
            ArrayList<Integer> bloco  = blocos.get(x);      
            for (int[] matriz1 : auxiliar) {
                int index = 0;
                for (int y = 0; y < bloco.size(); y++) 
                    index += (bloco.get(y) * matriz1[y]);     
                cifra += crypto.charAt(((index % length) + length) % length);
            }    
            anterior = auxiliar;
            if (coprimo < this.getMaxCoprimo(length))
               coprimo = this.getCoprimo(coprimo, length);  
            else 
               coprimo = 1;     
            auxiliar = this.getMatrCoprimo(matriz, coprimo);
            while ((getGcd(this.getDeterminante(auxiliar),length) != 1) && (count < 2)) {
                if (coprimo < this.getMaxCoprimo(length)) {
                    coprimo = this.getCoprimo(coprimo, length); 
                    auxiliar = getMatrCoprimo(matriz, coprimo);
                } else {
                    coprimo = 1;
                    count++;
                    auxiliar = matriz;
                }    
            }
        }
        return cifra;
    }
    
    private int[][] getMatrCoprimo(int[][] matriz, int coprimo) {
        int[][] auxiliar = new int[3][3];
        auxiliar[0][0] = matriz[0][0] * coprimo;
        auxiliar[0][1] = matriz[0][1] * coprimo;
        auxiliar[0][2] = matriz[0][2] * coprimo;
        auxiliar[1][0] = matriz[1][0] * coprimo;
        auxiliar[1][1] = matriz[1][1] * coprimo;
        auxiliar[1][2] = matriz[1][2] * coprimo;
        auxiliar[2][0] = matriz[2][0] * coprimo;
        auxiliar[2][1] = matriz[2][1] * coprimo;
        auxiliar[2][2] = matriz[2][2] * coprimo;
        return auxiliar;
    }
    
    private int[][] getMatrPermuta(int[][] matriz) {
        int[][] permuta = new int[3][3];
        permuta[0][0] = matriz[1][0];
        permuta[0][1] = matriz[1][1];
        permuta[0][2] = matriz[1][2];
        permuta[1][0] = matriz[2][0];
        permuta[1][1] = matriz[2][1];
        permuta[1][2] = matriz[2][2];
        permuta[2][0] = matriz[0][0];
        permuta[2][1] = matriz[0][1];
        permuta[2][2] = matriz[0][2];
        return permuta;
    }
    
    private int[][] somaMatrIdent(int[][] matriz) {
        int[][] ident = matriz;
        ident[0][0] = ident[0][0] + 1;
        ident[1][1] = ident[1][1] + 1;
        ident[2][2] = ident[2][2] + 1;
        return ident;
    }
    
    public int getCoprimo(int coprimo, int length) {
        for (int i = (coprimo + 1); i < length; i++) {
            if (getGcd(i,length) == 1) {
                coprimo = i;
                break;
            }
            if (i == length) {
                if (coprimo > 1)
                    getCoprimo(coprimo, length);
                else {
                    coprimo = 1;
                    break;
                }   
            }
        }
        return coprimo;
    }
    
    private int getMaxCoprimo(int length) {
        int coprimo = 1;
        for (int i = 1; i < length; i++)
            if (getGcd(i, length) == 1)
                coprimo = i;
        return coprimo;
    }
    
    private boolean matrInvertivel(int matriz[][], int length) {         
        return getGcd((matriz[0][0] * (matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1]) 
                     - matriz[0][1] * (matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0]) 
                     + matriz[0][2] * (matriz[1][1] * matriz[2][1] - matriz[1][1] * matriz[2][0]))
                , length) == 1;
    }
    
    private int getDeterminante(int matriz[][]) {
        return matriz[0][0] * (matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1]) 
             - matriz[0][1] * (matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0])
             + matriz[0][2] * (matriz[1][0] * matriz[2][1] - matriz[1][1] * matriz[2][0]);
    }
    
    private int[][] getMatrCofatores(int matriz[][]) {
        int cofatores[][] = new int[3][3];
        
        cofatores[0][0] = (matriz[1][1] * matriz[2][2] - matriz[1][2] * matriz[2][1]);
        cofatores[0][1] = (matriz[1][0] * matriz[2][2] - matriz[1][2] * matriz[2][0]) * -1;
        cofatores[0][2] = (matriz[1][0] * matriz[2][1] - matriz[1][1] * matriz[2][0]);
        
        cofatores[1][0] = (matriz[0][1] * matriz[2][2] - matriz[0][2] * matriz[2][1]) * -1;
        cofatores[1][1] = (matriz[0][0] * matriz[2][2] - matriz[0][2] * matriz[2][0]);
        cofatores[1][2] = (matriz[0][0] * matriz[2][1] - matriz[0][1] * matriz[2][0]) * -1;
        
        cofatores[2][0] = (matriz[0][1] * matriz[1][2] - matriz[0][2] * matriz[1][1]);
        cofatores[2][1] = (matriz[0][0] * matriz[1][2] - matriz[0][2] * matriz[1][0]) * -1;
        cofatores[2][2] = (matriz[0][0] * matriz[1][1] - matriz[0][1] * matriz[1][0]);
        
        return cofatores;
    }
    
    private int[][] getMatrAdjunta(int cofatores[][]) {
        int adjunta[][] = new int[3][3];
        adjunta[0][0] = cofatores[0][0];
        adjunta[0][1] = cofatores[1][0];
        adjunta[0][2] = cofatores[2][0];   
        adjunta[1][0] = cofatores[0][1];
        adjunta[1][1] = cofatores[1][1];
        adjunta[1][2] = cofatores[2][1];    
        adjunta[2][0] = cofatores[0][2];
        adjunta[2][1] = cofatores[1][2];
        adjunta[2][2] = cofatores[2][2];
        return adjunta;
    }
    
    private int[][] getMatrInversa(int adjunta[][], int length, int inv) {
        int inversa[][] = new int[3][3];
        
        inversa[0][0] = getMatrInvAux(adjunta[0][0], inv, length);
        inversa[0][1] = getMatrInvAux(adjunta[0][1], inv, length);
        inversa[0][2] = getMatrInvAux(adjunta[0][2], inv, length); 
        
        inversa[1][0] = getMatrInvAux(adjunta[1][0], inv, length); 
        inversa[1][1] = getMatrInvAux(adjunta[1][1], inv, length); 
        inversa[1][2] = getMatrInvAux(adjunta[1][2], inv, length);     
        
        inversa[2][0] = getMatrInvAux(adjunta[2][0], inv, length); 
        inversa[2][1] = getMatrInvAux(adjunta[2][1], inv, length); 
        inversa[2][2] = getMatrInvAux(adjunta[2][2], inv, length); 
        
        return inversa;
    }
    
    private int[][] getMatrInvMod(int[][] matriz, int coprimo, int mod) {
        matriz [0][0] = (invModular(coprimo, mod) * matriz[0][0]) % mod;
        matriz [0][1] = (invModular(coprimo, mod) * matriz[0][1]) % mod;
        matriz [0][2] = (invModular(coprimo, mod) * matriz[0][2]) % mod;
        matriz [1][0] = (invModular(coprimo, mod) * matriz[1][0]) % mod;
        matriz [1][1] = (invModular(coprimo, mod) * matriz[1][1]) % mod;
        matriz [1][2] = (invModular(coprimo, mod) * matriz[1][2]) % mod;
        matriz [2][0] = (invModular(coprimo, mod) * matriz[2][0]) % mod;
        matriz [2][1] = (invModular(coprimo, mod) * matriz[2][1]) % mod;
        matriz [2][2] = (invModular(coprimo, mod) * matriz[2][2]) % mod;
        return matriz;
    }
    
    public String getSenhaDinamica(ArrayList<ArrayList<Integer>> blocos, String crypto, String cifra) {        
        int matriz[][] = {{18, 11, 12},
                          {13, 14, 15},
                          {2, 16, 17}}; 
        int reserva[][] = {{19, 12, 13},
                           {14, 15, 16},
                           {3, 17, 18}}; 
        int permuta[][] = new int[3][3]; 
        int auxiliar[][]; 
        int cofatores[][]; 
        int adjunta[][]; 
        int inversa[][]; 
        int anterior[][] = new int[3][3];
        int length = crypto.length();

        StringBuilder senha = new StringBuilder();
        int coprimo = 1;
        auxiliar = matriz;
        int count = 0;
        
        for (ArrayList<Integer> bloco : blocos) {
            if (count == 2) {
                auxiliar = this.getMatrPermuta(anterior);
                if (getGcd(this.getDeterminante(auxiliar),length) != 1) {
                    auxiliar = this.somaMatrIdent(matriz);
                    if (getGcd(this.getDeterminante(auxiliar),length) != 1) {
                        auxiliar = reserva;
                        count++;
                    }
                }
            }
            if (count == 3) {
                auxiliar = matriz;
                count = 0;
            }
            cofatores = this.getMatrCofatores(auxiliar);
            adjunta = this.getMatrAdjunta(cofatores);
            int inv = invModular(this.getDeterminante(auxiliar), length);
            inversa = this.getMatrInversa(adjunta, length, inv);
        
            int[] decifrado = new int[3];         
            for (int i = 0; i < 3; i++) {
                decifrado[i] = 0;
                for (int j = 0; j < 3; j++) {
                    decifrado[i] += (inversa[i][j] * bloco.get(j));
                }
                decifrado[i] = ((decifrado[i] % length) + length) % length;
                senha.append(crypto.charAt(decifrado[i]));
            }
            anterior = auxiliar;
            if (coprimo < this.getMaxCoprimo(length))
               coprimo = this.getCoprimo(coprimo, length);  
            else
               coprimo = 1; 
           auxiliar = this.getMatrCoprimo(matriz, coprimo);
          
            while ((getGcd(this.getDeterminante(auxiliar),length) != 1) && (count < 2)) {
                if (coprimo < this.getMaxCoprimo(length)) {
                    coprimo = this.getCoprimo(coprimo, length); 
                    auxiliar = getMatrCoprimo(matriz, coprimo);
                } else {
                    coprimo = 1;
                    count++;
                    auxiliar = matriz;
                }    
            }
        }
        return senha.toString().replaceAll("Б", "");
    }
    
    public static int invModular(int a, int mod) {
        int inv = 0;
        int mult = 0;
        while ((a * mult) - (((a * mult) / mod) * mod) != 1)
            mult++;
        return mult;
    }

    public static int restoDivMod(int a, int b, int mod) {
        int mult = 1;
        int resto = 0;
        if (a < 0) {
            int invDet = ((a * b) - mod) * -1;
            while ((((invDet - (mult * mod)) * -1) + mod) <= mod) {
                resto = ((invDet - (mult * mod)) * -1) + mod;
                mult++;
            }
        } else {
            if ((a * b) <= mod)
                resto = (a * b) - mod;
            else {
                resto = a * b;
                resto = resto - ((resto / mod) * mod);
            }    
        } return resto;
    }
    
    // Greatest Common Divisor = Máximo Divisor Comum
    public static int getGcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static int getMatrInvAux(int num, int invMod, int mod) {    
        if (num < 0)
            return mod + (num * invMod) % mod;
        else
            return (num * invMod) % mod;
    }
    
    public String encryptHill(String senha) {
        senha = this.encrypt(senha);
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®Д¬«ª©¨§¦¥¤£¡ГБ"; 
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
        return this.getCifraDinamica(blocos, crypto, senha);
    }
    
    public String decryptHill(String cifra) {         
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®Д¬«ª©¨§¦¥¤£¡ГБ"; 
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
        return this.decrypt(this.getSenhaDinamica(blocos, crypto, cifra));
    }
    
    public String decryptAfim(String caract, int index) {
        int inv = 0;
        int length = caract.length() - 1;
        
        for (int x = length; x >= 0; x--) 
            if ((18 * x) % length == 1) { 
                inv = x;
                break;
            }
        return String.valueOf(caract.charAt((inv * ((index - 20 + length) % length)) % length));
    }

    public String encrypt(String senha) {
        String caract = "!#$%&'()*+,-./0123456789:;<=>?@abcdefghijklmnopqrstuvwxyz[]^_`ABCDEFGHIJKLMNOPQRSTUVWXYZ\\Б";
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®Д¬«ª©¨§¦¥¤£¡ГБ"; 
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
        String caract = "!#$%&'()*+,-./0123456789:;<=>?@abcdefghijklmnopqrstuvwxyz[]^_`ABCDEFGHIJKLMNOPQRSTUVWXYZ\\Б";
        String crypto = "Üúùø÷öõôóòñðïîíìëêéèçæåäãâáàßÝÛÚÙØ×ÖÕÔÓÒÑÐÏÎÍÌËÊÉÈÇÆÅÄÃÂÁÀ¿¾½¼»º¹¸·¶µ´³²±°¯®Д¬«ª©¨§¦¥¤£¡ГБ"; 
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
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package criptologia;

/**
 * @author Bruno Reinicke
 */
public class Criptologia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(new Cifragem().encryptHill("5U#*ulF3H.d+2D!67A3Kw`oX"));
        System.out.println(new Cifragem().decryptHill("À¿¿â¦îÛÝÚÃ£ñÖèëçä¾ÏëµÌ°Â").equals("5U#*ulF3H.d+2D!67A3Kw`oX"));
        
        System.out.println(new Cifragem().encryptHill("!#$%&'()*+,-./0123456789:;<=>?@abcdefghijklmnopqrstuvwxyz[]^_`ABCDEFGHIJKLMNOPQRSTUVWXYZ\\"));
        System.out.println(new Cifragem().decryptHill("ÙÏ©Ùº¨ºÃÌГú·µÏ©²ã¸ªòã½±½°ú¡ôÛ¤ËÛ³ç¨´ÒÃ®ÑÂéó¡Ý¼¬äêÏ¸ÃÃÜ·´ÄÈïÂДÉñóòÀ¡éìÓðÉ³ÉÑöõÖªÌÉ«Ü½ÄðòÔÉè")
                                                .equals("!#$%&'()*+,-./0123456789:;<=>?@abcdefghijklmnopqrstuvwxyz[]^_`ABCDEFGHIJKLMNOPQRSTUVWXYZ\\"));
    }    
}
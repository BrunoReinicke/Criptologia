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
       System.out.println(new Cifragem().encryptHill("hello"));
     //  System.out.println(new Cifragem().decryptHill("²ÂêÊïâ"));
     //  System.out.println(1 * (14 * 17 - 15 * 16) - 11 * (13 * 17 - 15 * 2) + 12 * (13 * 16 - 14 * 2)); 
       System.out.println(new Cifragem().restoDivMod(1186,89));
    }    
}
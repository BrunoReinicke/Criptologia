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
        System.out.println(new Cifragem().decryptHill("ßï÷ГÒè"));
        System.out.println(new Cifragem().encryptHill("hello"));
        System.out.println(new Cifragem().getMatrizInv(-266,47,90));
    }    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package criptologia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Bruno Reinicke
 */
public class Arquivo {

    public void percorreArq() {
        FileReader fr;
        BufferedReader reader;
        
        try {
            fr = new FileReader("C:/Users/Bruno Reinicke/Documents/Hacking/SENHAS/Usuario_B.txt");
            reader = new BufferedReader(fr);
            String line = "";       
           // int i = 1;
            
            while (line != null) {
                line = reader.readLine();
                if (line != null) 
                    this.SaveToFile(new Cifragem().decrypt(line));
                  /*  this.SaveToSQL("INSERT INTO USUARIO_C(ID, SENHA) "+
                                   "VALUES(" + i + ", '" + line + "');");
                i++;*/
            }
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SaveToFile(String line) {
        FileWriter fw;
        BufferedWriter writer;
        try {
            fw = new FileWriter("C:/Users/Bruno Reinicke/Documents/Hacking/SENHAS/Usuario_A.txt", true);
            writer = new BufferedWriter(fw);
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public void compArq() {
        FileReader fr;
        BufferedReader reader;
        FileReader fr2;
        BufferedReader reader2;
        try {
            fr = new FileReader("C:/Users/Bruno Reinicke/Documents/Hacking/SENHAS/Usuario_A.txt");
            reader = new BufferedReader(fr);
            fr2 = new FileReader("C:/Users/Bruno Reinicke/Documents/Hacking/SENHAS/Usuario_C.txt");
            reader2 = new BufferedReader(fr2);
            
            String line = "";
            String line2 = "";
            int i = 1;
            while ((line != null) && (line2 != null)) {
                line  = reader.readLine();
                line2 = reader2.readLine();
                
                if ((line == null) || (line2 == null))
                    break;
                if (!line.equals(line2)) {
                    System.out.println("Linha: " + i);
                    System.out.println(line);
                    System.out.println(line2);
                    break;
                }    
                i++;
            }
            System.out.println("passou");
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SaveToSQL(String line) {
        FileWriter fw;
        BufferedWriter writer;
        try {
            fw = new FileWriter("C:/Users/Bruno Reinicke/Documents/NetBeansProjects/RecursiveSQL/popular_tabela_usuario_c.sql", true);
            writer = new BufferedWriter(fw);
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
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
       /* System.out.println(new Cifragem().decryptHill(
                        "¶ë¬·õçÃâëÂ¬ÓÚíÒ¥´±º¤ëèäË¯¼æÙ¶ë¬·õçÃâë¶ë¬·õç¶ë¬·õçÃ").equals(
                      "#u>p$G3s9&Myn!1z?61lSR(oC.pk;T1%?`ZTxI_l*om;S4$mj1n"));*/
        System.out.println(new Cifragem().encryptHill("#u>p$G3s9&Myn!1z?61lSR(oC.pk;T1%?`ZTxI_l*om;S4$mj1n"));
    }    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fril;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author CuongPhan
 */
public class Fril {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        FrmLogin frm_login = new FrmLogin();
        JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(frm_login);
            frame.pack();
            frame.setVisible(true);
    }
    
}

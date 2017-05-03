/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fril;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author CuongPhan
 */
public class Test extends JFrame{
    
    public Test() throws MalformedURLException, IOException{
        this.setSize(400, 400);
        JLabel label = new JLabel();
        add(label);
        
        BufferedImage image = ImageIO.read(new URL("https://img.fril.jp/img/70447010/m/192497049.jpg?1488985483"));
        ImageIcon icon = new ImageIcon(image);
        label.setIcon(icon);      
        
    }
    public static void main(String[] args) throws IOException{
        Test test = new Test();
        test.setVisible(true);
    }
}

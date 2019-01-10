/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duckonline;

import javax.swing.JFrame;

/**
 *
 * @author abe
 */
public class DuckOnline {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        JFrame window = new JFrame("Duck Online");
        window.setContentPane(new GameWindow());
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
	window.pack();
	window.setVisible(true);
    }
    
}

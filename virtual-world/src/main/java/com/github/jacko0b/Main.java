package com.github.jacko0b;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        String gowno = "GÓWNO";
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(gowno + " App");
            JButton button = new JButton("Click Me!");

            button.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Hello " + gowno + "!"));

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.add(button);
            frame.setVisible(true);
        });
    }
}

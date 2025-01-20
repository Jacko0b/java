package com.github.jacko0b;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        String autor = "Jakub Sobota 200816";
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(autor);

            frame.setSize(300, 200);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
        World world = new World(20, 20);
        world.addCreature(new Wolf(2, 4, world));
        world.addCreature(new Sheep(4, 2, world));
        world.drawWorld();

    }
}

package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

class Wolf extends Animal {

    private static final Image wolfImage;

    static {
        wolfImage = new ImageIcon(
                Wolf.class.getResource("/images/wolf.png")
        ).getImage();
    }

    public Wolf(int x, int y, World world) {
        super(x, y, world);
        this.strength = 9;
        this.initiative = 5;

    }

    @Override
    public Image getImage() {
        return wolfImage;
    }

}

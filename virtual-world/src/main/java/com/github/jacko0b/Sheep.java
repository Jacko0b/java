package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Sheep extends Animal {

    private static final Image sheepImage;

    static {
        sheepImage = new ImageIcon(
                Sheep.class.getResource("/images/sheep.png")
        ).getImage();
    }

    public Sheep(int x, int y, World world) {
        super(x, y, world);
        this.strength = 4;
        this.initiative = 4;
    }

    @Override
    public Image getImage() {
        return sheepImage;
    }

}

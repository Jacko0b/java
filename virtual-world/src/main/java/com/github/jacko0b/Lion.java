package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Lion extends Animal {

    private static final Image lionImage;

    static {
        lionImage = new ImageIcon(
                Lion.class.getResource("/images/lion.png")
        ).getImage();
    }

    public Lion(int x, int y, World world) {
        super(x, y, world);
        this.strength = 11;
        this.initiative = 7;
        this.species = Species.LION;
    }

    @Override
    public Image getImage() {
        return lionImage;
    }

    @Override
    public void collision(Creature other) {
        if (other instanceof Animal && ((Animal) other).getStrength() < 5) {
            return;
        }
        super.collision(other);
    }
}

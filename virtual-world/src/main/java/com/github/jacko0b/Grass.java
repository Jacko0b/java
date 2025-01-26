package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Grass extends Plant {

    private static final Image grassImage;

    static {
        grassImage = new ImageIcon(
                Grass.class.getResource("/images/grass.png")
        ).getImage();
    }

    public Grass(int x, int y, World world) {
        super(x, y, world);
        this.strength = 0;
        this.species = Species.GRASS;
    }

    @Override
    protected Plant createNewPlant(int x, int y) {
        return new Grass(x, y, world);
    }

    @Override
    public Image getImage() {
        return grassImage;
    }
}

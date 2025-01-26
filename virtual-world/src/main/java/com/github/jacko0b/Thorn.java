package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Thorn extends Plant {

    private static final Image thornImage;

    static {
        thornImage = new ImageIcon(
                Thorn.class.getResource("/images/thorn.png")
        ).getImage();

    }

    public Thorn(int x, int y, World world) {
        super(x, y, world);
        this.strength = 2;
        this.species = Species.THORN;
        this.chances = 0.2;
    }

    @Override
    protected Plant createNewPlant(int x, int y) {
        return new Thorn(x, y, world);
    }

    @Override
    public Image getImage() {
        return thornImage;
    }

}

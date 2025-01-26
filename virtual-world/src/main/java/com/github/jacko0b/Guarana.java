package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Guarana extends Plant {

    private static final Image guaranaImage;
    private static final int STRENGTHBOOST = 3;

    static {
        guaranaImage = new ImageIcon(
                Guarana.class.getResource("/images/guarana.png")
        ).getImage();
    }

    public Guarana(int x, int y, World world) {
        super(x, y, world);
        this.species = Species.GUARANA;
    }

    @Override
    public Image getImage() {
        return guaranaImage;
    }

    @Override
    protected Plant createNewPlant(int x, int y) {
        return new Guarana(x, y, world);
    }

    @Override
    public void collision(Creature other) {
        other.setStrength(other.getStrength() + STRENGTHBOOST);
        world.getLogger().log(
                this.species + "(" + this.x + "," + this.y + ") Wzmacnia "
                + other.getSpecies() + "(" + ((Animal) other).getOldX() + "," + ((Animal) other).getOldY() + ")"
        );
        super.collision(other);
    }
}

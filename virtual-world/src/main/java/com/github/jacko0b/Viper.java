package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Viper extends Animal {

    private static final Image viperImage;

    static {
        viperImage = new ImageIcon(
                Viper.class.getResource("/images/viper.png")
        ).getImage();
    }

    public Viper(int x, int y, World world) {
        super(x, y, world);
        this.strength = 2;
        this.initiative = 3;
        this.species = Species.VIPER;
    }

    @Override
    public Image getImage() {
        return viperImage;
    }

    @Override
    public void collision(Creature other) {
        super.collision(other);
        if (other.getStrength() > this.strength && other.getSpecies() != this.species) {
            world.getLogger().log(
                    this.species + "(" + this.x + "," + this.y + ") zatruwa i zabija "
                    + other.getSpecies() + "(" + ((Animal) other).getOldX() + "," + ((Animal) other).getOldY() + ")"
            );
            world.deleteCreature(other);
        }

    }
}

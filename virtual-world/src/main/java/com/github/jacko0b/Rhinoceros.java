package com.github.jacko0b;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Rhinoceros extends Animal {

    private static final Image rhinocerosImage;
    private static final int BASE_INI = 1;
    private static final int BASE_STR = 2;
    private boolean encounteredThisTurn = false;
    private boolean encounteredLastTurn = false;

    static {
        rhinocerosImage = new ImageIcon(
                Rhinoceros.class.getResource("/images/rhinoceros.png")
        ).getImage();
    }

    public Rhinoceros(int x, int y, World world) {
        super(x, y, world);
        this.strength = BASE_STR;
        this.initiative = BASE_INI;
        this.species = Species.RHINOCEROS;
    }

    @Override
    public Image getImage() {
        return rhinocerosImage;
    }

    @Override
    public void action() {
        if (!encounteredLastTurn) {
            this.initiative = Math.min(99, this.initiative + 2);
            this.strength = Math.min(99, 2 * this.initiative);
        }
        encounteredThisTurn = false;

        super.action();

        encounteredLastTurn = encounteredThisTurn;
    }

    @Override
    public void collision(Creature other) {
        super.collision(other);
        encounteredThisTurn = true;
        if (!isDead()) {
            this.initiative = BASE_INI;
            this.strength = BASE_STR;
        }
    }
}

package com.github.jacko0b;

import java.util.List;

public abstract class Plant extends Creature {

    protected double chances = 0.1;

    public Plant(int x, int y, World world) {
        super(x, y, world);
        this.initiative = 0;
    }

    @Override
    public void action() {
        if (Math.random() < chances) {
            List<int[]> neighbors = world.getEmptyNeighbors(this.x, this.y);
            int[] freeField = world.getRandomFromList(neighbors);
            if (freeField != null) {
                int newX = freeField[0];
                int newY = freeField[1];
                world.setCreature(this.createNewPlant(newX, newY));
                world.getLogger().log("Roślina " + this.species + "(" + x + "," + y
                        + ") rozsiewa się do (" + newX + "," + newY + ")");
            }
        }
    }

    @Override
    public void collision(Creature other) {
        world.deleteCreature(this);
        if (other instanceof Animal animal) {
            animal.moveTo(this.x, this.y);
        }
    }

    protected abstract Plant createNewPlant(int x, int y);
}

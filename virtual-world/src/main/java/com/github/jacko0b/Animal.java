package com.github.jacko0b;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends Creature {

    private static final double REPRODUCTION_RATE = 0.2;
    protected boolean resting = false;
    protected int oldX;
    protected int oldY;

    public Animal(int x, int y, World world) {
        super(x, y, world);
        this.oldX = x;
        this.oldY = y;
    }

    @Override
    public void action() {

        List<int[]> neighbors = world.getNeighborsInRange(this.x, this.y);
        int[] chosen = world.getRandomFromList(neighbors);

        int newX = chosen[0];
        int newY = chosen[1];

        Creature occupant = world.getCreature(newX, newY);
        //wolne pole - przechodzi
        if (occupant == null) {
            moveTo(newX, newY);
        } //zajęte - kolizja
        else {
            occupant.collision(this);
        }
    }

    @Override
    public void collision(Creature other) {
        //odpoczynek...
        if (this.isResting() && Math.random() < REPRODUCTION_RATE) {
            this.setResting(false);
        }
        //kolizja z tym samym gatunkiem - reprodukcja
        if (this.species == other.getSpecies() && !other.isDead()) {
            if (!this.isResting() && !((Animal) other).isResting()) {
                reproduceWith((Animal) other);
                this.setResting(true);
                ((Animal) other).setResting(true);
                world.getLogger().log(
                        this.species + "(" + x + "," + y + ") rozmnaża się z "
                        + other.getSpecies() + "(" + other.getX() + "," + other.getY() + ")"
                );
            }
        } //kolizja z innym - walka
        else {
            if (other.getStrength() >= this.getStrength()) {
                // napastnik wygrywa
                world.getLogger().log(
                        other.getSpecies() + "(" + other.getX() + "," + other.getY() + ") zabija "
                        + this.species + "(" + x + "," + y + ")"
                );
                world.deleteCreature(this);
                ((Animal) other).moveTo(this.getX(), this.getY());

            } else {
                // ginie napastnik
                world.getLogger().log(
                        this.species + "(" + x + "," + y + ") zabija "
                        + other.getSpecies() + "(" + other.getX() + "," + other.getY() + ")"
                );
                world.deleteCreature(other);
            }
        }
    }

    private void reproduceWith(Animal partner) {
        List<int[]> freeSpots = new ArrayList<>();
        freeSpots.addAll(world.getEmptyNeighbors(this.x, this.y));
        freeSpots.addAll(world.getEmptyNeighbors(partner.getX(), partner.getY()));
        if (freeSpots.isEmpty()) {
            // brak miejsca - brak rozmnażania
            return;
        }

        // Wybieramy losowe
        int[] spot = world.getRandomFromList(freeSpots);

        Animal child = createChild(spot[0], spot[1]);
        world.setCreature(child);
    }

    protected Animal createChild(int x, int y) {
        switch (this.species) {
            case WOLF:
                return new Wolf(x, y, world);
            case SHEEP:
                return new Sheep(x, y, world);
            case VIPER:
                return new Viper(x, y, world);
            case LION:
                return new Lion(x, y, world);
            case RHINOCEROS:
                return new Rhinoceros(x, y, world);
            default:
                throw new IllegalArgumentException("createChild nie dodane dla " + this.species);
        }
    }

    protected void moveTo(int newX, int newY) {
        world.getMap().get(this.y).set(this.x, null);
        oldX = this.x;
        this.x = newX;
        oldY = this.y;
        this.y = newY;
        world.getMap().get(this.y).set(this.x, this);
    }

    public boolean isResting() {
        return resting;
    }

    public void setResting(boolean resting) {
        this.resting = resting;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }
}

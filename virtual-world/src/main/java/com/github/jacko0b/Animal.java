package com.github.jacko0b;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends Creature {

    private static final double REPRODUCTION_RATE = 0.2;
    protected boolean resting = false;

    public Animal(int x, int y, World world) {
        super(x, y, world);
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
            }
        } //kolizja z innym - walka
        else {
            if (other.getStrength() >= this.getStrength()) {
                // napastnik wygrywa
                world.deleteCreature(this);
                ((Animal) other).moveTo(this.getX(), this.getY());
            } else {
                // ginie napastnik
                world.deleteCreature(other);
            }
        }
    }

    @Override
    public void draw() {
        System.out.println("draw: " + this);
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
                throw new IllegalArgumentException("createChild not implemented for " + this.species);
        }
    }

    protected void moveTo(int newX, int newY) {
        world.getMap().get(this.y).set(this.x, null);
        this.x = newX;
        this.y = newY;
        world.getMap().get(this.y).set(this.x, this);
    }

    public boolean isResting() {
        return resting;
    }

    public void setResting(boolean resting) {
        this.resting = resting;
    }
}

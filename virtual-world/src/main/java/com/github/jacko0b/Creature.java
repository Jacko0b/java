package com.github.jacko0b;

import java.awt.Image;

public abstract class Creature {

    public enum Species {
        WOLF,
        SHEEP,
        LION,
        VIPER,
        RHINOCEROS,
        GRASS,
        GUARANA,
        THORN,
    }

    protected static int numberOfInstances = 0;
    protected int instanceNumber;
    protected int strength;
    protected int initiative;
    protected int x, y;
    protected World world;
    protected boolean dead;
    protected Species species;
    protected boolean collided;

    public Creature(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.dead = false;
        this.instanceNumber = numberOfInstances++;
        this.world = world;
        this.collided = false;
    }

    public abstract Image getImage();

    public abstract void action();

    public abstract void collision(Creature other);

    public boolean isDead() {
        return dead;
    }

    public void setIsDead(boolean isDead) {
        this.dead = isDead;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public static int getNumberOfInstances() {
        return numberOfInstances;
    }

    public int getInstanceNumber() {
        return instanceNumber;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public Species getSpecies() {
        return species;
    }
}

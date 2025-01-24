package com.github.jacko0b;

public abstract class Creature {

    public Creature(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.dead = false;
        this.instanceNumber = numberOfInstances++;
        this.world = world;
    }

    protected static int numberOfInstances = 0;
    protected int instanceNumber;
    protected int strength;

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    protected int initiative;

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    protected int x, y;
    protected World world;
    protected boolean dead;

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

    public abstract java.awt.Image getImage();

    public abstract void action();

    public abstract void collision(Creature other);

    public abstract void draw();

    public boolean isDead() {
        return dead;
    }

    public void setIsDead(boolean isDead) {
        this.dead = isDead;
    }

}

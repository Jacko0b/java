package com.github.jacko0b;

public abstract class Creature {

    public Creature(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.isDead = false;
        this.instanceNumber = numberOfInstances++;
        this.world = world;
    }

    protected static int numberOfInstances = 0;
    protected int instanceNumber;
    protected int strength;
    protected int initiative;
    protected int x, y;
    protected World world;
    protected boolean isDead;

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

    public abstract void action();

    public abstract void collision(Creature other);

    public abstract void draw();

    public boolean isIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

}

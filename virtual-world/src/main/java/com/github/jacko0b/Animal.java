package com.github.jacko0b;

public abstract class Animal extends Creature {

    public Animal(int x, int y, World world) {
        super(x, y, world);
    }

    @Override
    public void action() {
        System.out.println("akcja: " + this);
    }

    @Override
    public void collision(Creature other) {
        System.out.println("kolizja: " + this + " z:" + other);
    }

    @Override
    public void draw() {
        System.out.println("draw: " + this);
    }

}

package com.github.jacko0b;

import java.util.ArrayList;
import java.util.List;

public class World {

    private final List<Creature> creatures;

    public List<Creature> getMap() {
        return creatures;
    }

    private final int width;
    private final int height;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.creatures = new ArrayList<>();
    }

    public void addCreature(Creature creature) {
        try {
            if (!isInRange(creature.getX(), creature.getY())) {
                throw new IllegalArgumentException("Nie można dodać organizmu poza granicami świata!");
            }
            creatures.add(creature);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeTurn() {

    }

    public void drawWorld() {
        for (Creature c : creatures) {
            c.draw();
            System.out.println("instance number:" + c.instanceNumber + " no of instances" + Creature.numberOfInstances);
        }
    }

    private boolean isInRange(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}

package com.github.jacko0b;

import java.util.ArrayList;
import java.util.List;

public class World {

    private final List<List<Creature>> map;

    public World(int width, int height) {
        map = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Creature> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(null);
            }
            map.add(row);
        }
    }

    public void makeTurn() {

    }

    public void setCreature(Creature creature) {
        if (isInRange(creature.getX(), creature.getY())) {
            if (map.get(creature.getY()).get(creature.getX()) == null) {
                map.get(creature.getY()).set(creature.getX(), creature);
            } else {
                throw new IllegalArgumentException("Pole(" + creature.getX() + "," + creature.getY() + ") jest zajęte!");
            }
        } else {
            throw new IllegalArgumentException("Nie można dodać organizmu poza granicami świata!");
        }
    }

    public void setCreature(Creature creature, int x, int y) {
        if (isInRange(x, y)) {
            if (map.get(y).get(x) == null) {
                map.get(y).set(x, creature);
            } else {
                throw new IllegalArgumentException("Pole(" + x + "," + y + ") jest zajęte!");
            }
        } else {
            throw new IllegalArgumentException("Nie można dodać organizmu poza granicami świata!");
        }
    }

    public void deleteCreature(Creature creature) {
        int x = creature.getX();
        int y = creature.getY();
        if (isInRange(x, y)) {
            map.get(y).set(x, null);
            creature.setIsDead(true);
        } else {
            throw new IllegalArgumentException("Nie można usunąć organizmu poza granicami świata!");
        }
    }

    public Creature getCreature(int x, int y) {
        if (isInRange(x, y)) {
            return map.get(y).get(x);
        } else {
            throw new IllegalArgumentException("Nie można pobrać organizmu spoza mapy!");
        }
    }

    private boolean isInRange(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public List<List<Creature>> getMap() {
        return map;
    }

    public int getWidth() {
        return map.get(0).size();
    }

    public int getHeight() {
        return map.size();
    }

}

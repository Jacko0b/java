package com.github.jacko0b;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class World {

    public final String[] creatureNames = {"WOLF", "SHEEP", "LION", "RHINOCEROS", "VIPER", "GRASS", "GUARANA", "THORN"};

    private final List<List<Creature>> map;
    private int turnCounter;
    private final WorldLogger logger;

    public World(int width, int height, WorldLogger logger) {
        map = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Creature> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(null);
            }
            map.add(row);
        }
        this.turnCounter = 0;
        this.logger = logger;
    }

    public void makeTurn() {
        getLogger().clear();
        turnCounter++;
        //Tworzenie listy żywych organizmów
        List<Creature> creatures = new ArrayList<>();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Creature c = map.get(y).get(x);
                if (c != null && !c.isDead()) {
                    creatures.add(c);
                }
            }
        }

        //Sortowanie kolejki akcji
        //Anonimowa klasa 
        creatures.sort(new Comparator<Creature>() {

            @Override
            public int compare(Creature a, Creature b) {
                int cmp = Integer.compare(b.getInitiative(), a.getInitiative());
                if (cmp == 0) {
                    return Integer.compare(a.getInstanceNumber(), b.getInstanceNumber());
                }
                return cmp;
            }
        });

        //Wywołanie kolejki akcji
        for (Creature c : creatures) {
            if (!c.isDead()) {
                c.action();
            }
        }
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

    public void deleteAllCreatures() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                map.get(y).set(x, null);
            }
        }
    }

    public Creature getCreature(int x, int y) {
        if (isInRange(x, y)) {
            return map.get(y).get(x);
        } else {
            throw new IllegalArgumentException("Nie można pobrać organizmu spoza mapy!");
        }
    }

    public boolean isInRange(int x, int y) {
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

    // Lista wszystkich dostępnych sąsiednich pól w granicach mapy
    public List<int[]> getNeighborsInRange(int x, int y) {
        List<int[]> neighbors = new ArrayList<>();

        int[][] directions = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0}, {1, 0},
            {-1, 1}, {0, 1}, {1, 1}
        };

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (isInRange(newX, newY)) {
                neighbors.add(new int[]{newX, newY});
            }
        }
        return neighbors;
    }

    // Puste pola w zasięgu
    public List<int[]> getEmptyNeighbors(int x, int y) {
        List<int[]> neighbors = getNeighborsInRange(x, y);
        Iterator<int[]> it = neighbors.iterator();
        while (it.hasNext()) {
            int[] neighbor = it.next();
            if (getCreature(neighbor[0], neighbor[1]) != null) {
                it.remove();
            }
        }
        return neighbors;
    }

    // Losowe pole z listy
    public int[] getRandomFromList(List<int[]> coords) {
        if (coords.isEmpty()) {
            return null;
        }
        int rand = (int) (Math.random() * coords.size());
        return coords.get(rand);
    }

    public void addCreature(String creatureName, int x, int y) {
        Creature newOne = null;

        switch (creatureName) {
            case "WOLF":
                newOne = new Wolf(x, y, this);
                break;
            case "SHEEP":
                newOne = new Sheep(x, y, this);
                break;
            case "LION":
                newOne = new Lion(x, y, this);
                break;
            case "RHINOCEROS":
                newOne = new Rhinoceros(x, y, this);
                break;
            case "VIPER":
                newOne = new Viper(x, y, this);
                break;
            case "THORN":
                newOne = new Thorn(x, y, this);
                break;
            case "GRASS":
                newOne = new Grass(x, y, this);
                break;
            case "GUARANA":
                newOne = new Guarana(x, y, this);
                break;
            default:
                break;
        }

        if (newOne != null) {
            setCreature(newOne);
        }
    }

    public int getTurnCounter() {
        return this.turnCounter;
    }

    public WorldLogger getLogger() {
        return logger;
    }

    public void setTurnCounter(int turnCounter) {
        this.turnCounter = turnCounter;
    }
}

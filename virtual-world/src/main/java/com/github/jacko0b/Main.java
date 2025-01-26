package com.github.jacko0b;

public class Main {

    public static void main(String[] args) {
        World world = new World(20, 20);
        WorldRenderer worldRenderer = new WorldRenderer(world);

        //todo fabryka
        world.setCreature(new Wolf(1, 2, world));
        world.setCreature(new Wolf(3, 2, world));
        world.setCreature(new Wolf(4, 2, world));
        world.setCreature(new Sheep(19, 19, world));
        world.setCreature(new Sheep(5, 5, world));
        world.setCreature(new Viper(2, 2, world));
        world.setCreature(new Viper(2, 3, world));
        world.setCreature(new Viper(3, 3, world));
        world.setCreature(new Lion(2, 8, world));
        world.setCreature(new Lion(2, 9, world));
        world.setCreature(new Lion(2, 10, world));
        world.setCreature(new Rhinoceros(16, 9, world));
        world.setCreature(new Rhinoceros(16, 10, world));
        world.setCreature(new Rhinoceros(16, 11, world));
        world.setCreature(new Grass(6, 5, world));
        world.setCreature(new Grass(10, 10, world));
        world.setCreature(new Guarana(2, 5, world));
        world.setCreature(new Thorn(12, 13, world));
        //-------------

        worldRenderer.initFrame();
        worldRenderer.repaint();

    }
}

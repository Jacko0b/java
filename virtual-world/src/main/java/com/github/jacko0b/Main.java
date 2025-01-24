package com.github.jacko0b;

public class Main {

    public static void main(String[] args) {
        World world = new World(20, 20);
        WorldRenderer worldRenderer = new WorldRenderer(world);
        //WorldLogger

        //todo fabryka
        world.setCreature(new Wolf(1, 2, world));
        world.setCreature(new Sheep(19, 19, world));
        world.setCreature(new Sheep(5, 5, world));
        world.setCreature(new Viper(2, 2, world));
        world.setCreature(new Lion(2, 8, world));
        world.setCreature(new Rhinoceros(6, 9, world));
        world.setCreature(new Grass(6, 5, world));
        world.setCreature(new Grass(10, 10, world));
        //-------------

        
        worldRenderer.initFrame();
        worldRenderer.repaint();

    }
}

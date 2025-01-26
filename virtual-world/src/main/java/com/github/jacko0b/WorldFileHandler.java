package com.github.jacko0b;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WorldFileHandler {

    private final World world;
    private final WorldRenderer worldRenderer;

    public WorldFileHandler(World world, WorldRenderer worldRenderer) {
        this.world = world;
        this.worldRenderer = worldRenderer;
    }

    // Format zapisu:
    // Aktualna tura
    // Creatures...
    public void saveToFile(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println(world.getTurnCounter());
            for (int y = 0; y < world.getHeight(); y++) {
                for (int x = 0; x < world.getWidth(); x++) {
                    Creature c = world.getCreature(x, y);
                    if (c != null) {
                        pw.println(c.getSpecies() + " "
                                + c.getStrength() + " "
                                + c.getInitiative() + " "
                                + x + " " + y);
                    }
                }
            }
            worldRenderer.savingSuccess();
        } catch (IOException e) {
            worldRenderer.errorWhileSaving();
        }
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            //ustawienie tury z pliku
            String line = br.readLine();
            world.setTurnCounter(Integer.parseInt(line));

            //usuniecie starych postaci i dodanie tych z pliku
            world.deleteAllCreatures();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String species = parts[0];
                int strength = Integer.parseInt(parts[1]);
                int initiative = Integer.parseInt(parts[2]);
                int x = Integer.parseInt(parts[3]);
                int y = Integer.parseInt(parts[4]);

                world.addCreature(species, x, y);

                Creature c = world.getCreature(x, y);
                if (c != null) {
                    c.setStrength(strength);
                    c.setInitiative(initiative);
                }
            }
            worldRenderer.loadingSuccess();
        } catch (IOException | NumberFormatException e) {
            worldRenderer.errorWhileLoading();
        }
    }
}

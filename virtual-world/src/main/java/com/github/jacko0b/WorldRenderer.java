package com.github.jacko0b;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class WorldRenderer extends JPanel {

    private final World world;
    private final WorldFileHandler worldFile;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenWidth = screenSize.width;
    private final int screenHeight = (int) Math.floor(screenSize.height * 0.85f);
    private final int cellSize;
    private JTextArea logArea;

    public WorldRenderer(World world) {
        this.world = world;
        this.worldFile = new WorldFileHandler(world, this);
        this.cellSize = Math.min(screenWidth / world.getWidth(), screenHeight / world.getHeight());
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e);
            }
        });
    }

    private void handleClick(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int clickedCellX = mouseX / cellSize;
        int clickedCellY = mouseY / cellSize;

        if (!world.isInRange(clickedCellX, clickedCellY)) {
            return;
        }

        Creature occupant = world.getCreature(clickedCellX, clickedCellY);
        if (occupant != null) {
            return;
        }

        showAddCreatureMenu(e, clickedCellX, clickedCellY);
    }

    private void showAddCreatureMenu(MouseEvent e, int cellX, int cellY) {
        JPopupMenu popup = new JPopupMenu("Dodaj organizm");

        for (String c : world.creatureNames) {
            JMenuItem item = new JMenuItem(c);
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ev) {
                    world.addCreature(c, cellX, cellY);
                    repaint();
                }
            });
            popup.add(item);
        }

        popup.show(this, e.getX(), e.getY());
    }

    public JFrame initFrame() {
        JFrame frame = new JFrame("Jakub Sobota 200816");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton nextTurnButton = new JButton("Nowa tura");
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                world.makeTurn();
                repaint();

                updateLogArea();
            }
        });
        buttonPanel.add(nextTurnButton);

        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showSaveDialog(WorldRenderer.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    worldFile.saveToFile(file.getAbsolutePath());
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton loadButton = new JButton("Wczytaj");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(WorldRenderer.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    worldFile.loadFromFile(file.getAbsolutePath());
                    repaint();
                }
            }
        });
        buttonPanel.add(loadButton);

        frame.add(buttonPanel, BorderLayout.PAGE_END);

        JPanel rightPanel = new JPanel(new BorderLayout());

        logArea = new JTextArea(30, 69);
        logArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(logArea);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(rightPanel, BorderLayout.EAST);

        frame.setSize(screenWidth, screenHeight);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        return frame;
    }

    private void updateLogArea() {
        logArea.setText("");

        List<String> msgs = world.getLogger().getMessages();

        for (String m : msgs) {
            logArea.append(m + "\n");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWorld(g);
        drawTurnCounter(g);
    }

    private void drawWorld(Graphics g) {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);

                Creature creature = world.getCreature(x, y);
                if (creature != null) {
                    drawCreature(creature, g, x * cellSize, y * cellSize, cellSize);
                }
            }
        }
    }

    private void drawCreature(Creature creature, Graphics g, int x, int y, int size) {
        Image image = creature.getImage();
        if (image != null) {
            g.drawImage(image, x, y, size, size, this);
        } else {
            g.setColor(Color.RED);
            g.fillOval(x, y, size, size);
        }

    }

    private void drawTurnCounter(Graphics g) {
        String text = "Turn: " + world.getTurnCounter();

        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        int x = getWidth() - textWidth - 10;
        int y = getHeight() - textHeight - 10;

        g.setColor(Color.BLACK);
        g.drawString(text, x, y);
    }

    public void errorWhileLoading() {
        showPopupMessage("Błąd podczas wczytywania!");
    }

    public void errorWhileSaving() {
        showPopupMessage("Błąd podczas zapisu!");
    }

    public void loadingSuccess() {
        showPopupMessage("Wczytano poprawnie!");
    }

    public void savingSuccess() {
        showPopupMessage("Zapisano poprawnie!");
    }

    private void showPopupMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Komunikat", JOptionPane.INFORMATION_MESSAGE);
    }
}

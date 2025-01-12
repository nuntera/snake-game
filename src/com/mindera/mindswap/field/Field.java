package com.mindera.mindswap.field;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.mindera.mindswap.game_objects.fruit.Fruit;
import com.mindera.mindswap.game_objects.obstacle.Obstacle;
import com.mindera.mindswap.game_objects.snake.Snake;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages the game field display and drawing operations.
 * Uses Lanterna library for terminal-based graphics.
 */
public final class Field {

    // Display characters for different game elements
    private static final String BORDER_STRING = "▒";
    private static final String SNAKE_BODY_STRING = "#";
    private static final String SNAKE_HEAD_STRING = "0";
    private static final String FRUIT_STRING = "@";
    private static final String OBSTACLE_STRING = "X";

    private static int width;              // Field width
    private static int height;             // Field height
    private static Screen screen;          // Lanterna screen instance
    private static ScreenWriter screenWriter;  // Helper for writing to screen
    private static Set<Obstacle> obstacles;    // Collection of all obstacles

    /**
     * Private constructor to prevent instantiation
     */
    private Field() {
    }

    /**
     * Initializes the game field with specified dimensions
     */
    public static void init(int width, int height) {

        screen = TerminalFacade.createScreen();

        Field.width = width;
        Field.height = height;
        screen.getTerminal().getTerminalSize().setColumns(width);
        screen.getTerminal().getTerminalSize().setRows(height);

        screenWriter = new ScreenWriter(screen);
        screen.setCursorPosition(null);
        screen.startScreen();

        obstacles = new HashSet<>();

        drawWalls();
        screen.refresh();
    }

    /**
     * Draws the snake on the screen
     * Changes color to red if snake is dead
     */
    public static void drawSnake(Snake snake) {

        Terminal.Color snakeColor = Terminal.Color.GREEN;

        if (!snake.isAlive()) {
            snakeColor = Terminal.Color.RED;
        }

        Position head = snake.getHead();

        for (Position p : snake.getFullSnake()) {
            if (!p.equals(head)) {
                screen.putString(p.getCol(), p.getRow(), SNAKE_BODY_STRING, snakeColor, null);
            } else {
                screen.putString(p.getCol(), p.getRow(), SNAKE_HEAD_STRING, snakeColor, null);
            }
        }
        screen.refresh();
    }

    /**
     * Clears the snake's tail position
     */
    public static void clearTail(Snake snake) {
        Position tail = snake.getTail();
        screen.putString(tail.getCol(), tail.getRow(), " ", null, null);
    }

    /**
     * Draws the border walls of the field
     */
    private static void drawWalls() {
        for (int i = 0; i < width; i++) {
            screenWriter.drawString(i, 0, BORDER_STRING);
            screenWriter.drawString(i, height - 1, BORDER_STRING);
        }

        for (int j = 0; j < height; j++) {
            screenWriter.drawString(0, j, BORDER_STRING);
            screenWriter.drawString(width - 1, j, BORDER_STRING);
        }
    }

    public static Key readInput() {
        return screen.readInput();
    }

    public static void drawFruit(Fruit fruit) {
        screen.putString(fruit.getPosition().getCol(), fruit.getPosition().getRow(), FRUIT_STRING,
                Terminal.Color.MAGENTA, null);
        screen.refresh();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    // New additions
    public static void drawObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        screen.putString(obstacle.getPosition().getCol(), obstacle.getPosition().getRow(), OBSTACLE_STRING,
                Terminal.Color.WHITE, null);
        screen.refresh();
    }

    public static Set<Obstacle> getObstacles() {
        return obstacles;
    }

    public static void close() {
        if (screen != null) {
            screen.stopScreen();
        }
    }
}

package com.mindera.mindswap;

/**
 * Entry point for the Snake game application.
 * Initializes and starts the game with default settings.
 */
public class Main {

    public static void main(String[] args) {
        Game game = new Game(100, 25, 100);
        try {
            game.start();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}

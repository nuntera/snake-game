package com.mindera.mindswap.game_objects.snake;

import com.mindera.mindswap.field.Position;

import java.util.LinkedList;

/**
 * Represents the player's snake in the game.
 * Manages snake movement and body segments.
 */
public class Snake {
    private final static int SNAKE_INITIAL_SIZE = 10;
    private Direction direction;    // Current movement direction
    private boolean alive;         // Snake's life status
    private LinkedList<Position> body;  // Snake body segments

    /**
     * Creates a new snake with default size and position
     */
    public Snake() {
        this.alive = true;
        direction = Direction.LEFT;
        body = new LinkedList<>();
        for (int i = 0; i < SNAKE_INITIAL_SIZE; i++) {
            body.add(new Position(i + 50, 12));
        }
    }

    /**
     * Increases snake size by adding a segment at the tail
     */
    public void increaseSize() {
        body.add(new Position(this.getTail().getRow(), this.getTail().getCol()));
    }

    /**
     * Moves snake in specified direction
     */
    public void move(Direction direction) {
        // Change direction
        this.direction = direction;

        // Get the current head position
        Position head = body.getFirst();
        Position newHead;

        // Determine new head position based on direction
        switch (direction) {
            case UP:
                newHead = new Position(head.getCol(), head.getRow() - 1);
                break;
            case DOWN:
                newHead = new Position(head.getCol(), head.getRow() + 1);
                break;
            case LEFT:
                newHead = new Position(head.getCol() - 1, head.getRow());
                break;
            default:
                newHead = new Position(head.getCol() + 1, head.getRow());
                break;
        }

        // Add the new head to the snake's body
        body.addFirst(newHead);

        // Remove the last segment of the snake's body to simulate movement
        body.removeLast();
    }

    public void move() {
        move(direction);
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public Position getHead() {
        return body.getFirst();
    }

    public Position getTail() {
        return body.getLast();
    }

    public LinkedList<Position> getFullSnake() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSnakeSize() {
        return body.size();
    }

    @Override
    public String toString() {
        return "Snake{" +
                "direction=" + direction +
                ", alive=" + alive +
                ", head=" + body.getFirst() +
                '}';
    }
}

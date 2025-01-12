package com.mindera.mindswap.game_objects.fruit;

import com.mindera.mindswap.field.Position;

/**
 * Represents a fruit that the snake can eat to grow and score points.
 */
public class Fruit {
    private Position position;  // Fruit's position on field

    /**
     * Creates new fruit at specified position
     */
    public Fruit(Position position) {
        this.position = position;
    }

    /**
     * @return Current position of the fruit
     */
    public Position getPosition() {
        return position;
    }
}

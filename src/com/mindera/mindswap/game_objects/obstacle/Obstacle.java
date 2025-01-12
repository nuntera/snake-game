package com.mindera.mindswap.game_objects.obstacle;

import com.mindera.mindswap.field.Position;

/**
 * Represents an obstacle in the game field.
 * Obstacles are stationary objects that kill the snake on collision.
 */
public class Obstacle {
    private Position position;  // Obstacle's position on field

    /**
     * Creates new obstacle at specified position
     */
    public Obstacle(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Obstacle obstacle = (Obstacle) obj;
        return position.equals(obstacle.position);
    }

    public Position getPosition() {
        return position;
    }
}

package com.mindera.mindswap.game_objects.obstacle;

import com.mindera.mindswap.field.Position;

public class Obstacle {
    private Position position;

    public Obstacle(Position position) {
        this.position = position;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Obstacle obstacle = (Obstacle) obj;
        return position.equals(obstacle.position);
    }


    public Position getPosition() {
        return position;
    }
}

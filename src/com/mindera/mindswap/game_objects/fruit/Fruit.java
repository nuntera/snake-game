package com.mindera.mindswap.game_objects.fruit;

import com.mindera.mindswap.field.Position;

public class Fruit {
    private Position position;

    public Fruit(Position position) {
        this.position = position;
    }


    public Position getPosition() {
        return position;
    }
}

package com.mindera.mindswap.field;

import java.util.Objects;

/**
 * Represents a position on the game field using row and column coordinates.
 */
public class Position {
    private int row;    // Row position (y-coordinate)
    private int col;    // Column position (x-coordinate)

    /**
     * Creates a new position with given coordinates
     * @param col Column (x) position
     * @param row Row (y) position
     */
    public Position(int col, int row) {
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return col == position.col && row == position.row;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ')';
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }
}

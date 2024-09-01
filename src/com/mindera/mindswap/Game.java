package com.mindera.mindswap;

import com.googlecode.lanterna.input.Key;
import com.mindera.mindswap.field.Field;
import com.mindera.mindswap.field.Position;
import com.mindera.mindswap.game_objects.fruit.Fruit;
import com.mindera.mindswap.game_objects.obstacle.Obstacle;
import com.mindera.mindswap.game_objects.snake.Direction;
import com.mindera.mindswap.game_objects.snake.Snake;


public class Game {
    private static final int OBSTACLE_INCREMENT = 10;
    private static final int POINTS_FOR_NEW_OBSTACLE = 30;

    private Snake snake;
    private Fruit fruit;
    private int delay;
    private int score;
    private int nextObstacleScoreThreshold;

    public Game(int cols, int rows, int delay) {
        Field.init(cols, rows);
        this.delay = delay;
        snake = new Snake();
        score = 0;
        nextObstacleScoreThreshold = POINTS_FOR_NEW_OBSTACLE;
    }


    public void start() throws InterruptedException {
        generateFruit(); // uncomment when it's time to introduce fruits

        while (snake.isAlive()) {
            Thread.sleep(delay);
            Field.clearTail(snake);
            moveSnake();
            checkCollisions();
            Field.drawSnake(snake);
        }
        gameOver();
    }

    public static void generateObstacles(int numberOfObstacles) {
        for (int i = 0; i < numberOfObstacles; i++) {
            int col = RandomGenerator.generate(3, Field.getWidth() - 3);
            int row = RandomGenerator.generate(3, Field.getHeight() - 3);
            Position pos = new Position(col, row);
            Obstacle obstacle = new Obstacle(pos);
            if (!Field.getObstacles().contains(obstacle)) {
                Field.drawObstacle(obstacle);
            }
        }
    }

    private void generateFruit() {
        int row = RandomGenerator.generate(5, Field.getHeight() - 3);
        int column = RandomGenerator.generate(5, Field.getWidth() - 3);
        //System.out.println("row = " + row);
        //System.out.println("column = " + column);
        this.fruit = new Fruit(new Position(column, row));

        Field.drawFruit(fruit);
    }

    private void moveSnake() {
        Key k = Field.readInput();
        Direction newDirection = null;

        if (k != null) {
            switch (k.getKind()) {
                case ArrowUp:
                    newDirection = Direction.UP;
                    break;

                case ArrowDown:
                    newDirection = Direction.DOWN;
                    break;

                case ArrowLeft:
                    newDirection = Direction.LEFT;
                    break;

                case ArrowRight:
                    newDirection = Direction.RIGHT;
                    break;
            }

            if (newDirection != null && isValidDirectionChange(newDirection)) {
                snake.move(newDirection);
                return;
            }
        }
        snake.move();
    }

    private void incrementScore() {
        score += 10;
        // Check if reached the next threshold for adding obstacles
        if (score >= nextObstacleScoreThreshold) {
            generateObstacles(OBSTACLE_INCREMENT);
            nextObstacleScoreThreshold += POINTS_FOR_NEW_OBSTACLE; // Update the threshold for the next increment
            System.out.println("nextObstacleScoreThreshold: " + nextObstacleScoreThreshold);
            System.out.println("POINTS_FOR_NEW_OBSTACLE: " + POINTS_FOR_NEW_OBSTACLE);
        }
    }

    private void gameOver() {
        System.out.println("Game Over");
        System.out.println("Your score: " + score);
        // Todo - ascii art
    }

    private boolean isValidDirectionChange(Direction newDirection) {
        Direction currentDirection = snake.getDirection();
        return !((currentDirection == Direction.UP && newDirection == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDirection == Direction.UP) ||
                (currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDirection == Direction.LEFT));
    }

    private void checkCollisions() {
        // Check collision with walls
        if (snake.getHead().getCol() == 0 || snake.getHead().getRow() == 0 ||
                snake.getHead().getCol() == Field.getWidth() - 1 || snake.getHead().getRow() == Field.getHeight() - 1) {
            snake.die();
        }

        // Check collision with snake itself
        for (int i = 1; i < snake.getSnakeSize(); i++) {
            if (snake.getHead().equals(snake.getFullSnake().get(i))) {
                snake.die();
            }
        }

        // Check collision with obstacles
        for (Obstacle obstacle : Field.getObstacles()) {
            if (snake.getHead().equals(obstacle.getPosition())) {
                snake.die();
            }
        }

        // Check collision with fruit
        //System.out.println("Snake:"+snake.getHead());
        //System.out.println("Fruit:"+fruit.getPosition());
        if (snake.getHead().equals(fruit.getPosition())) {
            snake.increaseSize();
            incrementScore();
            generateFruit();
        }
    }


    public int getScore() {
        return score;
    }
}

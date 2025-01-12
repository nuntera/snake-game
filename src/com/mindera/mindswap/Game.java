package com.mindera.mindswap;

import com.googlecode.lanterna.input.Key;
import com.mindera.mindswap.field.Field;
import com.mindera.mindswap.field.Position;
import com.mindera.mindswap.game_objects.fruit.Fruit;
import com.mindera.mindswap.game_objects.obstacle.Obstacle;
import com.mindera.mindswap.game_objects.snake.Direction;
import com.mindera.mindswap.game_objects.snake.Snake;

/**
 * Main game controller class that handles the game logic, including snake movement,
 * collision detection, scoring, and obstacle generation.
 */
public class Game {
    // Number of obstacles to add each time the score threshold is reached
    private static final int OBSTACLE_INCREMENT = 10;
    // Score required to generate new obstacles
    private static final int POINTS_FOR_NEW_OBSTACLE = 30;

    private Snake snake;          // The player's snake
    private Fruit fruit;          // Current fruit on the field
    private int delay;           // Game speed (milliseconds between moves)
    private int score;           // Player's current score
    private int nextObstacleScoreThreshold;  // Score needed for next obstacle generation

    /**
     * Initializes a new game with specified dimensions and speed.
     * @param cols Number of columns in the game field
     * @param rows Number of rows in the game field
     * @param delay Milliseconds between each game tick
     */
    public Game(int cols, int rows, int delay) {
        Field.init(cols, rows);
        this.delay = delay;
        snake = new Snake();
        score = 0;
        nextObstacleScoreThreshold = POINTS_FOR_NEW_OBSTACLE;
    }

    /**
     * Main game loop that runs until the snake dies.
     * Handles movement, collisions, and screen updates.
     */
    public void start() throws InterruptedException {
        generateFruit();

        while (snake.isAlive()) {
            Thread.sleep(delay);
            Field.clearTail(snake);
            moveSnake();
            checkCollisions();
            Field.drawSnake(snake);
        }
        gameOver();
    }

    /**
     * Generates a specified number of random obstacles on the field.
     * Ensures obstacles don't overlap with existing ones.
     */
    public static void generateObstacles(int numberOfObstacles) {
        int attempts = 0;
        int obstaclesCreated = 0;

        while (obstaclesCreated < numberOfObstacles && attempts < numberOfObstacles * 2) {
            int col = RandomGenerator.generate(3, Field.getWidth() - 3);
            int row = RandomGenerator.generate(3, Field.getHeight() - 3);
            Position pos = new Position(col, row);
            Obstacle obstacle = new Obstacle(pos);

            // Check if position is not occupied by snake or existing obstacles
            if (!Field.getObstacles().contains(obstacle)) {
                Field.drawObstacle(obstacle);
                obstaclesCreated++;
            }
            attempts++;
        }
    }

    /**
     * Generates a new fruit at a random position on the field.
     */
    private void generateFruit() {
        int row = RandomGenerator.generate(5, Field.getHeight() - 3);
        int column = RandomGenerator.generate(5, Field.getWidth() - 3);
        this.fruit = new Fruit(new Position(column, row));
        Field.drawFruit(fruit);
    }

    /**
     * Handles snake movement based on user input.
     * If no input is received, continues in current direction.
     */
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

    /**
     * Increases score and generates new obstacles when threshold is reached.
     */
    private void incrementScore() {
        score += 10;
        if (score >= nextObstacleScoreThreshold) {
            generateObstacles(OBSTACLE_INCREMENT);
            nextObstacleScoreThreshold += POINTS_FOR_NEW_OBSTACLE;
        }
    }

    /**
     * Handles game over state and cleanup.
     */
    private void gameOver() {
        System.out.println("Game Over");
        System.out.println("Your score: " + score);
        Field.close();
    }

    /**
     * Validates if the requested direction change is legal.
     * Prevents 180-degree turns.
     */
    private boolean isValidDirectionChange(Direction newDirection) {
        Direction currentDirection = snake.getDirection();
        return !((currentDirection == Direction.UP && newDirection == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDirection == Direction.UP) ||
                (currentDirection == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDirection == Direction.LEFT));
    }

    /**
     * Checks for collisions with walls, snake body, obstacles, and fruit.
     * Updates game state accordingly.
     */
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
        if (snake.getHead().equals(fruit.getPosition())) {
            snake.increaseSize();
            incrementScore();
            generateFruit();
        }
    }

    /**
     * @return Current game score
     */
    public int getScore() {
        return score;
    }
}

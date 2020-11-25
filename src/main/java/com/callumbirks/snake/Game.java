package com.callumbirks.snake;

import javafx.scene.input.KeyCode;

import java.util.Random;

public class Game {
    private com.callumbirks.snake.Snake snake;
    private Food food;
    private int score;
    private final int WIDTH;
    private final int HEIGHT;
    private static final Random rand = new Random();

    public Game(int width, int height, int pixelSize) {
        snake = new com.callumbirks.snake.Snake(5, 5, pixelSize);
        WIDTH = width;
        HEIGHT = height;
        food = new Food();
        food.setPos(generateFoodPos());
        score = 0;
    }

    public void moveSnake() {
        snake.move();
        snake.updatePartDirections();
    }

    public void eat() {
        snake.eat();
        food.setPos(generateFoodPos());
        score++;
    }

    public int getScore() {
        return score;
    }

    public boolean isSnakeCrashed() {
        if(!isSnakeInBounds())
            return true;
        for(com.callumbirks.snake.SnakePart part : snake.getBody()) {
            if(!part.equals(snake.getHead())) {
                if (part.getX() == snake.getX() && part.getY() == snake.getY())
                    return true;
            }
        }
        return false;
    }

    public com.callumbirks.snake.Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public boolean isSnakeAtFood() {
        return (snake.getX() == food.getX() && snake.getY() == food.getY());
    }

    public void changeDirection(KeyCode code) {
        switch(code) {
            case UP,W -> {
                if (!(snake.getDirection() == Direction.DOWN))
                    snake.setDirection(Direction.UP);
            }
            case RIGHT,D -> {
                if(!(snake.getDirection() == Direction.LEFT))
                    snake.setDirection(Direction.RIGHT);
            }
            case DOWN,S -> {
                if(!(snake.getDirection() == Direction.UP))
                    snake.setDirection(Direction.DOWN);
            }
            case LEFT,A -> {
                if(!(snake.getDirection() == Direction.RIGHT))
                    snake.setDirection(Direction.LEFT);
            }
        }
    }

    private boolean isSnakeInBounds() {
        return (snake.getX() >= 0 &&
                snake.getY() >= 0 &&
                snake.getX() <= WIDTH - 1 &&
                snake.getY() <= HEIGHT - 1);
    }

    private int[] generateFoodPos() {
        int foodX;
        int foodY;
        do {
            foodX = rand.nextInt(WIDTH);
            foodY = rand.nextInt(HEIGHT);
        } while(insideSnake(foodX, foodY));
        return new int[] { foodX, foodY };
    }

    private boolean insideSnake(int foodX, int foodY) {
        for(com.callumbirks.snake.SnakePart part : snake.getBody()) {
            if(part.getX() == foodX && part.getY() == foodY)
                return true;
        }
        return false;
    }
}

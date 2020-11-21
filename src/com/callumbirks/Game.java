package com.callumbirks;

import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Random;

public class Game {
    private Snake snake = new Snake();
    private int[] food;
    private final Random rand = new Random();
    private final int width;
    private final int height;

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void moveSnake() {
        snake.move();
    }

    public void checkFood() {
        if(isSnakeAtFood()) {
            snake.eat();
            generateFood();
        }
    }

    public int[] getFood() {
        return food;
    }

    private boolean isSnakeAtFood() {
        return (snake.getX() == food[0] && snake.getY() == food[1]);
    }

    public void generateFood() {
        int[] foodPos;
        do {
            foodPos = new int[]{(rand.nextInt(
                    (width / Snake.PIXEL_SIZE)) * Snake.PIXEL_SIZE), (rand.nextInt((height) / Snake.PIXEL_SIZE)) * Snake.PIXEL_SIZE};
        } while (snake.getSnakeParts().contains(foodPos));
        food = foodPos;
    }

    public boolean isSnakeCrashed() {
        boolean crashed = false;
        for (int i = 0; i < snake.getSize(); i++) {
            crashed = !isSnakeInBounds() || (i > 0 && snake.getSnakeParts().get(i)[0] == snake.getX() && snake.getSnakeParts().get(i)[1] == snake.getY());
            if(crashed)
                return crashed;
        }
        return crashed;
    }

    private boolean isSnakeInBounds() {
        return  !(snake.getX() < 0 ||
                snake.getY() < 0 ||
                snake.getX() > width - Snake.PIXEL_SIZE ||
                snake.getY() > height - Snake.PIXEL_SIZE);
    }

    public int getSnakeSize() {
        return snake.getSize();
    }

    public List<int[]> getSnakeParts() {
        return snake.getSnakeParts();
    }

    public void changeSnakeDirection(KeyCode keyCode) {
        snake.changeDirection(keyCode);
    }
}

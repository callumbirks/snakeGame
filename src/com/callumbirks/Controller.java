package com.callumbirks;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Controller {

    @FXML
    private StackPane container;
    @FXML
    private Canvas gameCanvas;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    private Timeline mTimeline;
    private Snake snakeObj;
    private GraphicsContext gc;
    private int[] mFoodPos;
    private Random rand = new Random();

    private void resetTimeline() {
        if (mTimeline != null && mTimeline.getStatus() == Animation.Status.RUNNING)
            mTimeline.stop();
    }

    public void playGame() {
        container.getStyleClass().add("play");
        resetTimeline();
        gc = gameCanvas.getGraphicsContext2D();
        mTimeline = new Timeline();
        snakeObj = new Snake();
        mTimeline.setCycleCount(Timeline.INDEFINITE);
        mTimeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(100), e -> {
                    snakeObj.move();
                    render();
                    checkSnakeBounds();
                    checkFood();
                }));
        gameCanvas.requestFocus();
        mTimeline.play();
        generateFood();
    }

    private void checkFood() {
        if(snakeObj.getX() == mFoodPos[0] && snakeObj.getY() == mFoodPos[1]) {
            snakeObj.eat();
            generateFood();
        }
    }

    private void checkSnakeBounds() {
        if (snakeObj.getX() < 0 ||
                snakeObj.getY() < 0 ||
                snakeObj.getX() > WIDTH - Snake.PIXEL_SIZE ||
                snakeObj.getY() > HEIGHT - Snake.PIXEL_SIZE)
            pauseGame();
        for (int i = 0; i < snakeObj.getSnakeParts().size(); i++) {
            if (i > 0 && snakeObj.getSnakeParts().get(i)[0] == snakeObj.getX() && snakeObj.getSnakeParts().get(i)[1] == snakeObj.getY())
                pauseGame();
        }
    }

    private void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.LIME);
        List<int[]> snakeParts = snakeObj.getSnakeParts();
        for (int[] coords : snakeParts) {
            gc.fillRect(coords[0], coords[1], Snake.PIXEL_SIZE, Snake.PIXEL_SIZE);
        }
        gc.setFill(Color.WHITE);
        if (mFoodPos != null)
            gc.fillOval(mFoodPos[0], mFoodPos[1], 20, 20);
    }

    private void changeSnakeDirection(KeyCode keyCode) {
        if (keyCode.isArrowKey())
            snakeObj.changeDirection(keyCode);
        else
            pauseGame();
    }

    public void generateFood() {
        int[] foodPos;
        do {
            foodPos = new int[]{(rand.nextInt(
                    (WIDTH / Snake.PIXEL_SIZE)) * Snake.PIXEL_SIZE), (rand.nextInt((HEIGHT) / Snake.PIXEL_SIZE)) * Snake.PIXEL_SIZE};
        } while (snakeObj.getSnakeParts().contains(foodPos));
        mFoodPos = foodPos;
    }

    private void pauseGame() {
        mTimeline.pause();
        container.getStyleClass().remove("play");
        gc.clearRect(0, 0, WIDTH, HEIGHT);
    }

    public void showHighScores(ActionEvent actionEvent) {
    }

    public void quit() {
        System.exit(0);
    }

    public void handleKeyPress(KeyEvent keyEvent) {
        if (mTimeline != null) {
            if (keyEvent.getCode().isArrowKey())
                changeSnakeDirection(keyEvent.getCode());
//            if (keyEvent.getCode() == KeyCode.SPACE)
//                snakeObj.eat();
            else if (keyEvent.getCode() == KeyCode.ESCAPE)
                pauseGame();
        }
    }
}

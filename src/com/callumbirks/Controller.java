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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;

public class Controller {

    @FXML
    private StackPane container;
    @FXML
    private Canvas gameCanvas;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    private Timeline mTimeline;
    private GraphicsContext gc;

    private Game game;

    private void resetTimeline() {
        if (mTimeline != null && mTimeline.getStatus() == Animation.Status.RUNNING)
            mTimeline.stop();
    }

    public void btnPlayGame() {
        container.getStyleClass().add("play");
        resetTimeline();
        gc = gameCanvas.getGraphicsContext2D();
        game = new Game(WIDTH,HEIGHT);
        mTimeline = new Timeline();
        mTimeline.setCycleCount(Timeline.INDEFINITE);
        mTimeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(100), e -> {
                    game.moveSnake();
                    render();
                    checkSnakeBounds();
                    game.checkFood();
                }));
        gameCanvas.requestFocus();
        mTimeline.play();
        game.generateFood();
    }


    private void checkSnakeBounds() {
        if(game.isSnakeCrashed())
            pauseGame();
    }

    private void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.LIME);
        List<int[]> snakeParts = game.getSnakeParts();
        for (int[] coords : snakeParts) {
            gc.fillRect(coords[0], coords[1], Snake.PIXEL_SIZE, Snake.PIXEL_SIZE);
        }
        gc.setFill(Color.WHITE);
        if (game.getFood() != null)
            gc.fillOval(game.getFood()[0], game.getFood()[1], 20, 20);
    }

    private void changeSnakeDirection(KeyCode keyCode) {
        if (keyCode.isArrowKey())
            game.changeSnakeDirection(keyCode);
        else
            pauseGame();
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
            else if (keyEvent.getCode() == KeyCode.ESCAPE)
                pauseGame();
        }
    }
}

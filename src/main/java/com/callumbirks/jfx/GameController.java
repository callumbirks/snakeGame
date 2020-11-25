package com.callumbirks.jfx;

import com.callumbirks.snake.Direction;
import com.callumbirks.snake.Game;
import com.callumbirks.snake.SnakePart;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;

    private static final int PIXEL_SIZE = 20;

    private Game game;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int width = (int) canvas.getWidth() / PIXEL_SIZE;
        int height = (int) canvas.getHeight() / PIXEL_SIZE;

        gc = canvas.getGraphicsContext2D();

        game = new Game(width, height, PIXEL_SIZE);


        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(80), e -> {
            if (game.isSnakeCrashed()) pauseTimeline();
            if (game.isSnakeAtFood()) game.eat();
            canvas.requestFocus();
            game.moveSnake();
            render();
        }));
        timeline.play();
        canvas.requestFocus();
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.LIME);
        for (SnakePart part : game.getSnake().getBody()) {
            gc.fillRect(
                    calcPoint(part.getX()),
                    calcPoint(part.getY()),
                    PIXEL_SIZE,
                    PIXEL_SIZE);
        }
        gc.setFill(Color.WHITE);
        gc.fillOval(game.getFood().getX() * PIXEL_SIZE, game.getFood().getY() * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
    }

    private int calcPoint(int coord) {
        return coord * PIXEL_SIZE;
    }

    public void pauseTimeline() {
        timeline.pause();
    }

    public void handleKeyPress(KeyEvent keyEvent) {
        game.changeDirection(keyEvent.getCode());
    }
}

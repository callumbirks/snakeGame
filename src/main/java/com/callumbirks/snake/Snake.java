package com.callumbirks.snake;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final com.callumbirks.snake.SnakePart head;
    private List<com.callumbirks.snake.SnakePart> body;

    public Snake(int x, int y, int pixelSize) {
        head = new com.callumbirks.snake.SnakePart(x, y, com.callumbirks.snake.Direction.RIGHT);
        body = new ArrayList<>();
        body.add(head);
    }

    public int getX() {
        return head.getX();
    }

    public int getY() {
        return head.getY();
    }

    public com.callumbirks.snake.SnakePart getHead() {
        return head;
    }

    public int getSize() {
        return body.size();
    }

    public List<com.callumbirks.snake.SnakePart> getBody() {
        return body;
    }

    public void eat() {
        com.callumbirks.snake.SnakePart lastPart = body.get(body.size() - 1);
        com.callumbirks.snake.Direction lastPartDirection = lastPart.getDirection();
        int newX = calculateNewPartX(lastPartDirection, lastPart.getX());
        int newY = calculateNewPartY(lastPartDirection, lastPart.getY());

        body.add(new com.callumbirks.snake.SnakePart(newX, newY, lastPartDirection));
    }

    public void updatePartDirections() {
        if(!(body.size() > 1))
            return;
        com.callumbirks.snake.Direction lastDirection = com.callumbirks.snake.Direction.RIGHT;
        for(com.callumbirks.snake.SnakePart part : body) {
            com.callumbirks.snake.Direction prevDirection = part.getDirection();
            if(part != head) {
                part.setDirection(lastDirection);
            }
            lastDirection = prevDirection;
        }
    }

    public void setDirection(com.callumbirks.snake.Direction direction) {
        head.setDirection(direction);
    }

    public com.callumbirks.snake.Direction getDirection() {
        return head.getDirection();
    }

    public void move() {
        for(com.callumbirks.snake.SnakePart part : body) {
            part.move();
        }
    }

    private int calculateNewPartX(com.callumbirks.snake.Direction direction, int x) {
        return switch(direction) {
            case UP, DOWN -> x;
            case RIGHT -> x -= 1;
            case LEFT -> x += 1;
        };
    }

    private int calculateNewPartY(com.callumbirks.snake.Direction direction, int y) {
        return switch(direction) {
            case LEFT, RIGHT -> y;
            case UP -> y += 1;
            case DOWN -> y -= 1;
        };
    }
}

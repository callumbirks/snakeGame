package com.callumbirks;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public static final int PIXEL_SIZE = 20;
    private int mSize;
    private Direction mDirection;
    private List<int[]> mSnakeParts;

    public Snake() {
        mSize = 1;
        mDirection = Direction.RIGHT;
        mSnakeParts = new ArrayList<>();
        mSnakeParts.add(new int[]{100, 100});
    }

    public int getX() {
        return mSnakeParts.get(0)[0];
    }

    public int getY() {
        return mSnakeParts.get(0)[1];
    }

    public int getSize() {
        return mSize;
    }

    public void eat() throws IllegalArgumentException {
        this.mSize++;
        int[] coords;
        if(mSnakeParts.size() > 3) {
            Direction lastPartDirection = null;
            int[] lastPart = mSnakeParts.get(mSnakeParts.size() - 1);
            int[] lastButOnePart = mSnakeParts.get(mSnakeParts.size() - 2);
            int xDiff = lastButOnePart[0] - lastPart[0];
            int yDiff = lastButOnePart[1] - lastPart[1];
            if (xDiff == PIXEL_SIZE)
                lastPartDirection = Direction.RIGHT;
            if (xDiff == -PIXEL_SIZE)
                lastPartDirection = Direction.LEFT;
            if (yDiff == PIXEL_SIZE)
                lastPartDirection = Direction.UP;
            if (yDiff == -PIXEL_SIZE)
                lastPartDirection = Direction.DOWN;
            if (lastPartDirection == null)
                return;
            coords = switch (lastPartDirection) {
                case UP -> new int[]{lastPart[0], lastPart[1] + PIXEL_SIZE};
                case RIGHT -> new int[]{(mSnakeParts.get(mSnakeParts.size() - 1))[0] - PIXEL_SIZE, mSnakeParts.get(0)[1]};
                case DOWN -> new int[]{mSnakeParts.get(0)[0], (mSnakeParts.get(mSnakeParts.size() - 1))[1] - PIXEL_SIZE};
                case LEFT -> new int[]{(mSnakeParts.get(mSnakeParts.size() - 1))[0] + PIXEL_SIZE, mSnakeParts.get(0)[1]};
            };
        }
        else {
            coords = switch (mDirection) {
                case UP -> new int[]{mSnakeParts.get(0)[0], mSnakeParts.get(mSnakeParts.size() - 1)[1] + PIXEL_SIZE};
                case RIGHT -> new int[]{(mSnakeParts.get(mSnakeParts.size() - 1))[0] - PIXEL_SIZE, mSnakeParts.get(0)[1]};
                case DOWN -> new int[]{mSnakeParts.get(0)[0], (mSnakeParts.get(mSnakeParts.size() - 1))[1] - PIXEL_SIZE};
                case LEFT -> new int[]{(mSnakeParts.get(mSnakeParts.size() - 1))[0] + PIXEL_SIZE, mSnakeParts.get(0)[1]};
            };
        }
        mSnakeParts.add(coords);
    }

    public void move() {
        switch (mDirection) {
            case UP -> {
                for(int i = mSnakeParts.size() - 1; i >= 0; i--) {
                    if(i == 0)
                        mSnakeParts.get(i)[1] -= (PIXEL_SIZE);
                    else {
                        mSnakeParts.get(i)[0] = mSnakeParts.get(i - 1)[0];
                        mSnakeParts.get(i)[1] = mSnakeParts.get(i - 1)[1];
                    }

                }
            }
            case RIGHT -> {
                for(int i = mSnakeParts.size() - 1; i >= 0; i--) {
                    if(i == 0)
                        mSnakeParts.get(i)[0] += (PIXEL_SIZE);
                    else {
                        mSnakeParts.get(i)[0] = mSnakeParts.get(i - 1)[0];
                        mSnakeParts.get(i)[1] = mSnakeParts.get(i - 1)[1];
                    }

                }
            }
            case DOWN -> {
                for(int i = mSnakeParts.size() - 1; i >= 0; i--) {
                    if(i == 0)
                        mSnakeParts.get(i)[1] += (PIXEL_SIZE);
                    else {
                        mSnakeParts.get(i)[0] = mSnakeParts.get(i - 1)[0];
                        mSnakeParts.get(i)[1] = mSnakeParts.get(i - 1)[1];
                    }
                }
            }
            case LEFT -> {
                for(int i = mSnakeParts.size() - 1; i >= 0; i--) {
                    if(i == 0)
                        mSnakeParts.get(i)[0] -= (PIXEL_SIZE);
                    else {
                        mSnakeParts.get(i)[0] = mSnakeParts.get(i - 1)[0];
                        mSnakeParts.get(i)[1] = mSnakeParts.get(i - 1)[1];
                    }
                }
            }
        }
    }

    public Direction getDirection() {
        return mDirection;
    }

    public List<int[]> getSnakeParts() {
        return mSnakeParts;
    }

    public void changeDirection(KeyCode keyCode) {
        switch (keyCode) {
            case UP -> {
                if (mDirection != Direction.DOWN)
                    mDirection = Direction.UP;
            }
            case RIGHT -> {
                if (mDirection != Direction.LEFT)
                    mDirection = Direction.RIGHT;
            }
            case DOWN -> {
                if (mDirection != Direction.UP)
                    mDirection = Direction.DOWN;
            }
            case LEFT -> {
                if (mDirection != Direction.RIGHT)
                    mDirection = Direction.LEFT;
            }
        }
    }

    public void setDirection(Direction direction) {
        mDirection = direction;
    }
}

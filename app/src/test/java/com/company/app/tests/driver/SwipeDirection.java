package com.company.app.tests.driver;

/**
 * An enum, which i used to define user swipe directions across app screen
 */

public enum SwipeDirection {
    LEFT(150, 450, 0, 0),
    RIGHT(450, 150, 0, 0);

    private int startX, endX, startY, endY;

    SwipeDirection(int startX, int endX, int startY, int endY) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndY() {
        return endY;
    }

}

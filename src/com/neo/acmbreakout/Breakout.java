/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */

package com.neo.acmbreakout;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /* Method: run() */

    /**
     * Runs the Breakout program
     */
    public void run() {
        setupGame();
    }

    /**
     * setup the brick, the ball and the paddle and
     * add to the game screen
     */
    private void setupGame() {
        setupBricks();
        setupPaddle();
        //setupBall();
    }


    /**
     * create the paddle of the brick
     */
    private void setupPaddle() {
        paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFilled(true);
        add(paddle, (getWidth() - PADDLE_WIDTH) / 2,
                getHeight() - PADDLE_Y_OFFSET);
    }

    /**
     * setup the bricks for the game
     */
    private void setupBricks() {
        for (int i = 0; i < NBRICK_ROWS; i++) {
            double yPosition = i * (BRICK_HEIGHT + (BRICK_SEP / 2)) + BRICK_Y_OFFSET;
            createBrickRow(i, yPosition);
        }
    }

    /**
     * create a row of bricks
     *
     * @param rowNumber the current brick row
     * @param yPosition the current y offset
     */
    private void createBrickRow(int rowNumber, double yPosition) {
        for (int j = 0; j < NBRICKS_PER_ROW; j++) {
            GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
            brick.setFilled(true);
            if (rowNumber < 2) {
                brick.setFillColor(Color.RED);
            } else if (rowNumber < 4) {
                brick.setFillColor(Color.ORANGE);
            } else if (rowNumber < 6) {
                brick.setFillColor(Color.YELLOW);
            } else if (rowNumber < 8) {
                brick.setFillColor(Color.GREEN);
            } else {
                brick.setFillColor(Color.CYAN);
            }

            //  update the xposition of the brick with brick seperation
            //  and brick width
            double xPosition = j * (BRICK_WIDTH + (BRICK_SEP * 0.9)) + BRICK_SEP;
            add(brick, xPosition, yPosition);
        }
    }

    /**
     * private instance variables
     */
    private GRect paddle;
}

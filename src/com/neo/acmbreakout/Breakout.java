/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 */

package com.neo.acmbreakout;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.AudioClip;
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
     * Radius of the ball in pixels
     */
    private static final int BALL_DIAMETER = BALL_RADIUS * 2;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /** the animation delay */
    private static final int DELAY = 13;

    /* Method: run() */

    /**
     * Runs the Breakout program
     */
    public void run() {
        setupGame();
        while(!gameOver()) {
            moveBall();
            checkForCollision();
            pause(DELAY);
        }
    }

    /** checks the various conditions to test if the
     *  game is over
     * @return true if game is over
     */
    private boolean gameOver() {
        if (ballHitsBottom()) {
            return true;
        } return false;
    }

    private boolean ballHitsBottom() {
        return (ball.getY() >= (getHeight() - BALL_DIAMETER));
    }

    /** checks if the ball collides with environment
     *  count down life if ball collides with floor
     *  increment score if collide with bricks
     *  change direction if bounce off the wall and paddle
     */
    private void checkForCollision() {
        checkWallCollision();
        checkBrickPaddleCollision();
    }

    /** if the ball collides with a brick, remove the brick
     *  -accomplishes this by assuming the ball is a rectangle
     *  and checks the four corner of the rectangle
     *  */
    private void checkBrickPaddleCollision() {
        GObject collider = getCollidingObject();
        if (collider != null) {
            if (collider != paddle) {
                remove(collider);
            }
        }
    }

    /** checks if the object collides with a brick
     *  checks the four corners of the ball */
    private GObject getCollidingObject() {
        if (getElementAt(ball.getX(), ball.getY()) != null) {
            vy = - vy;
            bounceClip.play();
            return getElementAt(ball.getX(), ball.getY());
        } else if (getElementAt(ball.getX() + BALL_DIAMETER, ball.getY()) != null) {
            vy = - vy;
            bounceClip.play();
            return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY());
        } else if (getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER) != null) {
            vy = - vy;
            bounceClip.play();
            return getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER);
        } else if (getElementAt(ball.getX(), ball.getY() + BALL_DIAMETER) != null) {
            vy = - vy;
            return getElementAt(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER);
        }
        return null;
    }


    /** change the direction of the ball if it collides with
     *  any part of the wall
     */
    private void checkWallCollision() {
        //  change direction if ball collide with right wall
        if (ball.getX() >= (getWidth() - BALL_DIAMETER)) {
            vx = - vx;
        }

        //  change direction if ball collide with left wall
        if (ball.getX() <= BALL_DIAMETER) {
            vx = - vx;
        }

        //  change direction if ball collide with floor
        if (ball.getY() >= (getHeight() - BALL_DIAMETER)) {
            vy = - vy;
        }

        //  change direction if ball collide with roof
        if (ball.getY() <= BALL_DIAMETER) {
            vy = - vy;
        }
    }

    /** moves the ball across the game
     *  changes the direction of the ball if it
     *  collides with the wall of the game
     */
    private void moveBall() {
        if (newGame()) {
            vy = 3.0;
            vx = rgen.nextDouble(1.0, 3.0);
            //  the initial velocity of the ball should have a 50/50
            //  chance of being positive or negative
            if (rgen.nextBoolean(0.5)) vx = - vx;
        }

        ball.move(vx, vy);
    }

    private boolean newGame() {
        return (vy == 0 && vy == 0);
    }

    /**
     * setup the brick, the ball and the paddle and
     * add to the game screen
     */
    private void setupGame() {
        setupBricks();
        setupPaddle();
        setupBall();
        addMouseListeners();
    }

    /**
     * create the ball for the game
     */
    private void setupBall() {
        ball = new GOval(BALL_RADIUS * 2, BALL_RADIUS * 2);
        ball.setFilled(true);
        ball.setFillColor(Color.BLUE);
        add(ball, (getWidth() - (BALL_RADIUS * 2)) / 2,
                (getHeight() - (BALL_RADIUS * 2)) / 2);
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

    /** moving the mouse controls the paddle on the screen
     *  please note the mouse can only move if it is
     *  within the game screen  */
    public void mouseMoved(MouseEvent e) {
        if (e.getX() >= 0 && (e.getX() <= (getWidth() - PADDLE_WIDTH))) {
            double paddleLastLocation = paddle.getX();
            paddle.move(e.getX() - paddleLastLocation, 0);
        }
    }

    /**
     * private instance variables
     */
    private GRect paddle;
    private GOval ball;
    private double vx = 0;  //   the ball's initial x velocity
    private double vy = 0;  //   the ball's initial y velocity
    private RandomGenerator rgen = RandomGenerator.getInstance();
    private String filePath = "/home/neo/Documents/Breakout/";
    private String audioFile = filePath + "bounce.au";
    AudioClip bounceClip = MediaTools.loadAudioClip(audioFile);
}

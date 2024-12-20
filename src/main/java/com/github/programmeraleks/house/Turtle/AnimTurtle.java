package com.github.programmeraleks.house.Turtle;

import com.github.programmeraleks.house.Resources.Vector2;

import java.time.Duration;
import java.time.Instant;

/**
 * A custom turtle class that animates the movement of the turtle.
 * @author Aleksandr Stinchcomb
 */
public class AnimTurtle extends Turtle {
    protected static int fps = 20; // the frames-per-second of the animation
    private double speed = 100; // speed of the turtle in pixels-per-second & degrees-per-second (movement and turn speed of the turtle)
    
    public AnimTurtle(ModelDisplay modelDisplayer) {
        super(modelDisplayer);
    }
    public AnimTurtle(Picture picture) {
        super(picture);
    }
    public AnimTurtle(int x, int y, ModelDisplay modelDisplayer) {
        super(x, y, modelDisplayer);
    }
    public AnimTurtle(int x, int y, Picture picture) {
        super(x, y, picture);
    }
    public AnimTurtle(int x, int y, int a, ModelDisplay modelDisplayer) {
        super(x, y, modelDisplayer);
        super.turn(a);
    }
    public AnimTurtle(int x, int y, int a, Picture picture) {
        super(x, y, picture);
        super.turn(a);
    }

    public static int getFps() {return fps;}
    public static void setFps(int newFps) {fps = newFps;}

    // return the turtle speed
    public double getSpeed() {return speed;}
    // set the turtle speed
    public void setSpeed(double newSpeed) {speed = newSpeed;}

    // yields the current thread (in milliseconds)
    public void waitMil(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {}
    }

    // Calculates the time to wait between frames in order to maintain a certain FPS
    private long getWaitTime() {
        double rate = (1d / fps);
        return (long)(rate * 1000);
    }
    // waits between frames
    protected void waitAnim() {
        waitMil(getWaitTime());
    }

    @Override
    public void turn(double degrees) {
        double angle = 0d;
        double finalHeading = (getHeading() + degrees) % 360;
        
        double timeIncrement = (1000d/speed);
        double total = 0;

        Instant start = Instant.now();
        while (angle < degrees) {
            Instant end = Instant.now();
            long elapsed = Duration.between(start, end).toMillis();
            timeIncrement = (1000d/speed); // recalculate in case speed has changed

            start = end;
            total += elapsed;

            if (total >= timeIncrement) {
                int turnAmount = (int)(total/timeIncrement);
                total %= timeIncrement;
                
                if (turnAmount > (degrees - angle)) {
                    super.setHeading(finalHeading);
                    angle = degrees;
                } else {
                    super.turn(turnAmount);
                    angle += turnAmount;
                    waitAnim();
                }
            }
        }
        updateDisplay();
    }
    
    @Override
    public void forward(int pixels) {
        int distance = 0;

        double timeIncrement = (1000d/speed);
        double total = 0;

        Instant start = Instant.now();
        while (distance < pixels) {
            Instant end = Instant.now();
            long elapsed = Duration.between(start, end).toMillis();
            timeIncrement = (1000d/speed); // recalculate in case speed has changed

            start = end;
            total += elapsed;

            if (total >= timeIncrement) {
                int moveAmount = (int)(total/timeIncrement);
                total %= timeIncrement;
                
                if (moveAmount > (pixels - distance)) {
                    super.forward(pixels - distance);
                    distance = pixels;
                } else {
                    super.forward(moveAmount);
                    distance += moveAmount;
                    waitAnim();
                }
            }
        }
        updateDisplay();
    }

    public void setSize(Vector2 size) {super.setSize((int)size.x,(int)size.x);}
    //public void setSize(Vector2 size, double animateSpeed) {setSize((int)size.x,(int)size.x, animateSpeed);}
    @Override
    public void setSize(int width, int height) {super.setSize(width, height);}
    /*public void setSize(int width, int height, double animateSpeed) {
        Vector2 currentSize = getSize();
        Vector2 finalSize = new Vector2(width, height);
        Vector2 direction = new Vector2(currentSize, finalSize).unit();

        double distance = 0;
        double finalDistance = currentSize.magnitude(finalSize);

        double timeIncrement = (1000d/speed);
        double total = 0;

        Instant start = Instant.now();
        while (distance < finalDistance) {
            Instant end = Instant.now();
            long elapsed = Duration.between(start, end).toMillis();
            timeIncrement = (1000d/speed); // recalculate in case speed has changed

            start = end;
            total += elapsed;

            if (total >= timeIncrement) {
                int moveAmount = (int)(total/timeIncrement);
                total %= timeIncrement;
                
                if (moveAmount > (finalDistance - distance)) {
                    super.setSize(finalSize);
                    distance = finalDistance;
                } else {
                    currentSize = currentSize.add(direction.mul(moveAmount));
                    super.setSize(currentSize);
                    distance += moveAmount;
                    waitAnim();
                }
            }
        }
        updateDisplay();
    }*/

    /**
     * Overrides the parent method so that the turtle moves to the given destination over time.
     * Change in position over time equals movement, so I can thus call it animated.
     * @param x the x position to move to
     * @param y the y position to move to
     */
    @Override
    public void moveTo(int x, int y) {
        Vector2 currentPos = new Vector2(getXPos(), getYPos());
        Vector2 finalPos = new Vector2(x, y);
        Vector2 direction = new Vector2(currentPos, finalPos).unit();

        double distance = 0;
        double finalDistance = currentPos.magnitude(finalPos);

        double timeIncrement = (1000d / speed);
        double total = 0;

        // Runs a loop over time until the AnimTurtle reaches its destination.
        Instant start = Instant.now();

        while (distance < finalDistance) {
            Instant end = Instant.now(); //gets the elapsed time since the last loop
            long elapsed = Duration.between(start, end).toMillis();
            timeIncrement = (1000d / speed); // recalculate in case speed has changed

            start = end;
            total += elapsed;

            if (total >= timeIncrement) {
                int moveAmount = (int)(total/timeIncrement);
                total %= timeIncrement;
                
                if (moveAmount > (finalDistance - distance)) {
                    super.moveTo(finalPos);
                    distance = finalDistance;
                } else {
                    currentPos = currentPos.add(direction.mul(moveAmount));
                    super.moveTo(currentPos);
                    distance += moveAmount;
                    waitAnim();
                }
            }
        }
        updateDisplay();
    }

    /**
     * Acts as a simple wrapper that enables the {@link AnimTurtle} class to accept a {@link Vector2}
     * as the argument for the target position.
     * @param targetVector the location position to move to
     */
    public void moveTo(Vector2 targetVector) {
        moveTo((int)targetVector.x, (int)targetVector.y);
    }
}

package com.github.programmeraleks.house.Turtle;
/**
 * Class that represents a turtle which is similar to a Logo turtle.
 * This class inherts from SimpleTurtle and is for students
 * to add methods to.
 *
 * Copyright Georgia Institute of Technology 2004
 * @author Barb Ericson ericson@cc.gatech.edu
 */

import com.github.programmeraleks.house.Resources.Vector2;

import java.awt.*;

public class Turtle extends SimpleTurtle
{
  ////////////////// constructors ///////////////////////
  
  /** Constructor that takes the x and y and a picture to
   * draw on
   * @param x the starting x position
   * @param y the starting y position
   * @param picture the picture to draw on
   */
  public Turtle (int x, int y, Picture picture) 
  {
    // let the parent constructor handle it
    super(x,y,picture);
  }
  
  /** Constructor that takes the x and y and a model
   * display to draw it on
   * @param x the starting x position
   * @param y the starting y position
   * @param modelDisplayer the thing that displays the model
   */
  public Turtle (int x, int y, 
                 ModelDisplay modelDisplayer) 
  {
    // let the parent constructor handle it
    super(x,y,modelDisplayer);
  }
  
  /** Constructor that takes the model display
   * @param modelDisplay the thing that displays the model
   */
  public Turtle (ModelDisplay modelDisplay) 
  {
    // let the parent constructor handle it
    super(modelDisplay);
  }
  
  /**
   * Constructor that takes a picture to draw on
   * @param p the picture to draw on
   */
  public Turtle (Picture p)
  {
    // let the parent constructor handle it
    super(p);
  }
  
  /////////////////// methods ///////////////////////
  /**Method to go left by 100 pixels*/
  public void left()
  {
    left(100);
  }
  /**Method to move the turtle left the given number of pixels 
   * @param pixels the number of pixels to walk left
   */
  public void left(int pixels)
  {
    int oldX = getXPos();
    int oldY = getYPos();

    double heading = (getHeading() + 90)%360;
    
    int xPos = oldX + (int)(pixels * Math.sin(Math.toRadians(heading)));
    int yPos = oldY + (int)(pixels * -Math.cos(Math.toRadians(heading)));

    moveTo(xPos, yPos);
  }

  /**Method to go right by 100 pixels*/
  public void right()
  {
    right(100);
  }
  /**Method to move the turtle right the given number of pixels 
   * @param pixels the number of pixels to walk right
   */
  public void right(int pixels)
  {
    int oldX = getXPos();
    int oldY = getYPos();

    double heading = (getHeading() - 90)%360;
    
    int xPos = oldX + (int)(pixels * Math.sin(Math.toRadians(heading)));
    int yPos = oldY + (int)(pixels * -Math.cos(Math.toRadians(heading)));

    moveTo(xPos, yPos);
  }

  /**Method to move to turtle to the given vector location
   * @param targetVector the location vector to move to
   */
  public void moveTo(Vector2 targetVector) {super.moveTo((int)targetVector.x, (int)targetVector.y);}

  public Vector2 getPosition() {return new Vector2(getXPos(), getYPos());}
  
  public Vector2 getSize() {return new Vector2(getWidth(), getHeight());}
  
  public void setSize(Vector2 size) {
    setWidth((int)size.x);
    setHeight((int)size.y);
  }
  public void setSize(int width, int height) {
    setWidth(width);
    setHeight(height);
  }

  public void adjust(Vector2 targetVector) {
    boolean isDown = isPenDown();
    penUp();
    super.moveTo((int)targetVector.x, (int)targetVector.y);
    if (isDown) {penDown();}
  }
  public void adjust(Vector2 targetVector, double heading) {
    boolean isDown = isPenDown();
    penUp();
    moveTo(targetVector);
    setHeading(heading);
    if (isDown) {penDown();}
  }
  public void adjust(Vector2 targetVector, Color color) {
    boolean isDown = isPenDown();
    penUp();
    moveTo(targetVector);
    setPenColor(color);
    if (isDown) {penDown();}
  }
  public void adjust(Vector2 targetVector, double heading, Color color) {
    boolean isDown = isPenDown();
    penUp();
    moveTo(targetVector);
    setHeading(heading);
    setPenColor(color);
    if (isDown) {penDown();}
  }
  public void adjust(int x, int y) {
    boolean isDown = isPenDown();
    penUp();
    super.moveTo(x, y);
    if (isDown) {penDown();}
  }
  public void adjust(int x, int y, double heading) {
    boolean isDown = isPenDown();
    penUp();
    super.moveTo(x, y);
    setHeading(heading);
    if (isDown) {penDown();}
  }
  public void adjust(int x, int y, Color color) {
    boolean isDown = isPenDown();
    penUp();
    super.moveTo(x, y);
    setPenColor(color);
    if (isDown) {penDown();}
  }
  public void adjust(int x, int y, double heading, Color color) {
    boolean isDown = isPenDown();
    penUp();
    super.moveTo(x, y);
    setHeading(heading);
    setPenColor(color);
    if (isDown) {penDown();}
  }

  public void drawPatch(Vector2 location, int size) {drawPatch((int)location.x, (int)location.y, size);}
  public void drawPatch(Vector2 location, int size, double heading) {drawPatch((int)location.x, (int)location.y, size, heading);}
  public void drawPatch(int x, int y, int size) {
    getPen().drawPatch(x, y, size, getHeading());
    adjust(x, y);
    updateDisplay();
  }
  public void drawPatch(int x, int y, int size, double heading) {
    getPen().drawPatch(x, y, size, heading);
    adjust(x, y, heading);
    updateDisplay();
  }

  public void drawLine(Vector2 lineStart, Vector2 lineEnd) {
    adjust(lineStart);
    moveTo(lineEnd);
  }
  public void drawLine(int x1, int y1, int x2, int y2) {
    adjust(x1, y1);
    moveTo(x2, y2);
  }

  public void drawCircle(Vector2 circleCenter, int diameter) {
    float radius = (diameter / 2f);
    
    double heading = super.getHeading();
    setHeading(0);

    for (float i=0; i<360; i++) {
      int x = (int)(circleCenter.x+(radius*Math.cos(i)));
      int y = (int)(circleCenter.y+(radius*Math.sin(i)));
      double theta = Math.atan2(y-circleCenter.y, x-circleCenter.x);
      drawPatch(x, y, getPenWidth(), theta);
    }
    super.setHeading(heading);
  }
  public void drawCircle(int x, int y, int diameter) {drawCircle(new Vector2(x, y), diameter);}

  public void drawOval(Vector2 location, Vector2 size, boolean fill) {
    getPen().drawOval(location, size, fill);
  }
  public void drawOval(Vector2 location, int size, boolean fill) {
    getPen().drawOval(location, Vector2.one.mul(size), fill);
  }
  public void drawOval(int x, int y, int size, boolean fill) {
    getPen().drawOval(new Vector2(x, y), Vector2.one.mul(size), fill);
  }

  public void drawArc() {
    //g.drawArc(x, y, width, height, startAngle, arcAngle);
  }

  public void drop(Vector2 location, Picture picture) {
    adjust(location);
    drop(picture);
    updateDisplay();
  }
  public void drop(Vector2 location, double a, Picture picture) {
    adjust(location, a);
    drop(picture);
    updateDisplay();
  }
  public void drop(int x, int y, Picture picture) {
    adjust(x, y);
    drop(picture);
    updateDisplay();
  }
  public void drop(int x, int y, double a, Picture picture) {
    adjust(x, y, a);
    drop(picture);
    updateDisplay();
  }
} // this } is the end of class Turtle, put all new methods before this
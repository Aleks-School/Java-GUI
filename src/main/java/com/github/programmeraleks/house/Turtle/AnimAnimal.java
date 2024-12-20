package com.github.programmeraleks.house.Turtle;

import com.github.programmeraleks.house.Resources.Resources;
import com.github.programmeraleks.house.Resources.Vector2;
import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A child of the {@link AnimTurtle} class that enables using a custom image in place of the turtle.
 * @author Aleksandr Stinchcomb
 */
public class AnimAnimal extends AnimTurtle {
  private BufferedImage originalImage;

  private ArrayList<Pair<Vector2,Image[]>> stateImages = new ArrayList<Pair<Vector2,Image[]>>();
  private int currentState;

  /** Constructor that takes the x and y and a picture to
   * draw on
   * @param x the starting x position
   * @param y the starting y position
   * @param picture the picture to draw on
   */
  public AnimAnimal(int x, int y, Picture picture) {
    // let the parent constructor handle it
    super(x,y,picture);
  }
  
  /** Constructor that takes the x and y and a model
   * display to draw it on
   * @param x the starting x position
   * @param y the starting y position
   * @param modelDisplayer the thing that displays the model
   */
  public AnimAnimal(int x, int y, ModelDisplay modelDisplayer) {
    // let the parent constructor handle it
    super(x,y,modelDisplayer);
  }
  
  /** Constructor that takes the model display and image file path
   * @param modelDisplay the thing that displays the model
   * @param image the file path for the image
   */
  public AnimAnimal(ModelDisplay modelDisplay, String image) {
    // let the parent constructor handle it
    super(modelDisplay);
    setImage(image);
  }

  /** Constructor that takes the model display
   * @param modelDisplay the thing that displays the model
   */
  public AnimAnimal(ModelDisplay modelDisplay) {
    // let the parent constructor handle it
    super(modelDisplay);
  }
  
  /**
   * Constructor that takes a picture to draw on
   * @param p the picture to draw on
   */
  public AnimAnimal(Picture p) {
    // let the parent constructor handle it
    super(p);
  }

  public void goTo(int x, int y) {
    turnToFace(x, y);
    moveTo(x, y);
  }

  public void goTo(Vector2 target) {
    goTo((int)target.x, (int)target.y);
  }

  public Image getImage() {
    Pair<Vector2,Image[]> state;
    try {
      state = stateImages.get(currentState);
      setSize(state.getKey());

      double heading = getHeading();
    return ((heading % 180 < heading) || (heading % 360 < heading) || (heading < 0)) ? state.getValue()[1] : state.getValue()[0];
    } catch (IndexOutOfBoundsException e) {
      return originalImage;
    }
  }

  public void setImage(String filePath) {
    try {
      originalImage = Resources.getImage(filePath);
      Vector2 size = new Vector2(originalImage.getWidth(), originalImage.getHeight());
      
      clearStates();
      addState(size);
      setSize(size);
    } catch (IOException ignored) {}
  }
  public void setImage(Image image) {
    try {
      originalImage = Resources.toBufferedImage(image);
      Vector2 size = new Vector2(originalImage.getWidth(), originalImage.getHeight());
      
      clearStates();
      addState(size);
      setSize(size);
    } catch (InterruptedException ignored) {}
  }

  public int getNumStates() {return stateImages.size();}
  
  private synchronized void clearStates() {
    stateImages.clear();
    currentState = 0;
  }

  private synchronized Pair<Vector2,Image[]> getStateData(Vector2 size) {
    Image[] images = new Image[2];

    try {
      images[0] = originalImage.getScaledInstance((int)size.x, (int)size.y, Image.SCALE_SMOOTH);
      images[1] = Resources.flip(Resources.toBufferedImage(images[0]));
    } catch (Exception e) {
      images[1] = Resources.flip(originalImage);
    }

    return new Pair<>(size, images);
  }

  public synchronized void addState(Vector2 size) {
    stateImages.add(getStateData(size));
  }
  public synchronized void addState(int state, Vector2 size) {
    stateImages.set(state, getStateData(size));
  }

  public synchronized void setState(int state) {currentState = state;}
  public int getState() {return currentState;}


  /**
   * Method to paint the bird 
   * @param g the graphics context to paint on
   */
  public synchronized void paintComponent(Graphics g) {
    // cast to 2d object
    Graphics2D g2 = (Graphics2D)g;
    
    // if the turtle is visible
    if (isVisible()) {
      // save the current tranform
      AffineTransform oldTransform = g2.getTransform();

      Vector2 position = getPosition();
      Vector2 size = getSize();

      int xPos = (int)position.x;
      int yPos = (int)position.y;

      int width = (int)size.x;
      int height = (int)size.y;
      
      // rotate the turtle and translate to xPos and yPos
      double heading = getHeading();
      g2.rotate(Math.toRadians(heading),xPos,yPos);
      
      // determine the half width and height of the shell
      int halfWidth = (int) (width/2); // of shell
      int halfHeight = (int) (height/2); // of shell
      
      // draw the body
      g2.setColor(getBodyColor());
      if (getNumStates() > 0) {
        g2.drawImage(getImage(), xPos - halfWidth, yPos - halfHeight, null);
      }
      
      // draw the info string if the flag is true
      if (getShowInfo()) {drawInfoString(g2);}
      
      // reset the tranformation matrix
      g2.setTransform(oldTransform);
    }
    
    //  draw the pen
    getPen().paintComponent(g);
  }
}

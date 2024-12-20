package com.github.programmeraleks.house.Turtle;

import com.github.programmeraleks.house.Resources.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * This class represents a displayable path segment
 * it has a color, width, and a Line2D object
 * Copyright Georgia Institute of Technology 2005
 * @author Barb Ericson ericson@cc.gatech.edu
 */ 
public class PixelPatch
{
  //////////////// fields /////////////////////
  private Color color;
  private int area;
  private Vector2 location;
  private double heading;
  
  //////////////// constructors ///////////////
  
  /**
   * Constructor that takes the color, width, 
   * and location
   */
  public PixelPatch (Color theColor, int theArea, Vector2 theLocation) {
    this.color = theColor;
    this.area = theArea;
    this.location = theLocation;
    this.heading = 0;
  }
  /**
   * Constructor that takes the color, width, 
   * location, and heading
   */
  public PixelPatch (Color theColor, int theArea, Vector2 theLocation, double theHeading) {
    this.color = theColor;
    this.area = theArea;
    this.location = theLocation;
    this.heading = Math.toRadians(theHeading);
  }

  //////////////// methods ////////////////////
  
  /**
   * Method to paint this path segment
   * @param g the graphics context
   */
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    AffineTransform oldTransform = g2.getTransform();
    
    g2.setColor(this.color);
    g2.rotate(this.heading, this.location.x, this.location.y);
    g2.fillRect((int)this.location.x, (int)this.location.y, this.area, this.area);
    g2.setTransform(oldTransform);
  }
  
} // end of class
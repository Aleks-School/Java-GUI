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
public class Oval
{
  //////////////// fields /////////////////////
  private Color color;
  private Vector2 location;
  private Vector2 size;
  private boolean fill;
  
  //////////////// constructors ///////////////
  
  /**
   * Constructor that takes the color, width, 
   * and location
   */
  public Oval (Color color, Vector2 location, Vector2 size, boolean fill) {
    this.color = color;
    this.location = location;
    this.size = size;
    this.fill = fill;
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
    g2.rotate(0, this.location.x, this.location.y);
    if (this.fill) {
      g2.fillOval((int)this.location.x, (int)this.location.y, (int)this.size.x, (int)this.size.y);
    } else {
      g2.drawOval((int)this.location.x, (int)this.location.y, (int)this.size.x, (int)this.size.y);
    }
    g2.setTransform(oldTransform);
  }
  
} // end of class
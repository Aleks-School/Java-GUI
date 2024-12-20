package com.github.programmeraleks.house.Turtle;

import com.github.programmeraleks.house.Resources.Vector2;

import java.awt.*;

/**
 * This class represents a displayable path segment
 * it has a color, width, and a Line2D object
 * Copyright Georgia Institute of Technology 2005
 * @author Barb Ericson ericson@cc.gatech.edu
 */ 
public class Polygon {
  //////////////// fields /////////////////////
  private Color color;
  private int width;
  private boolean fill;
  private int[] xCoordinates;
  private int[] yCoordinates;

  private static Color clearColor = new Color(255,255,255);
  
  //////////////// constructors ///////////////
  
  /**
   * Constructor that takes the color and an array of points
   */
  public Polygon(Color color, int width, Vector2[] points, boolean fill) {
    this.color = color;
    this.width = width;
    this.fill = fill;

    this.xCoordinates = new int[points.length];
    this.yCoordinates = new int[points.length];

    int index = 0;
    for (Vector2 point : points) {
        xCoordinates[index] = (int)point.x;
        yCoordinates[index++] = (int)point.y;
    }
  }
  
  //////////////// methods ////////////////////
  
  /**
   * Method to paint this path segment
   * @param g the graphics context
   */
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    
    if (this.color.equals(clearColor)) {
      g2.clearRect((xCoordinates[0]+xCoordinates[2])/2, (yCoordinates[0]+yCoordinates[2])/2, Math.abs(xCoordinates[0]-xCoordinates[1]), Math.abs(yCoordinates[0]-yCoordinates[1]));
    } else {
      BasicStroke penStroke = new BasicStroke(this.width); 
    
      g2.setStroke(penStroke);
      g2.setColor(this.color);

      if (this.fill) {
        g2.fillPolygon(xCoordinates, yCoordinates, xCoordinates.length);
      } else {
        g2.drawPolygon(xCoordinates, yCoordinates, xCoordinates.length);
      }
    }
  }
  
} // end of class
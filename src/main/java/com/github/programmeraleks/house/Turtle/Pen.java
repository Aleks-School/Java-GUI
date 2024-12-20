package com.github.programmeraleks.house.Turtle;

import com.github.programmeraleks.house.Resources.Vector2;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** 
 * Class to represent a pen which has a color, width, 
 * and a list of path segments that it should draw.  
 * A pen also knows if it is up or down 
 * 
 * Copyright Georgia Institute of Technology 2004
 * @author Barb Ericson ericson@cc.gatech.edu
 */
public class Pen 
{
  ////////////////// fields //////////////////////
  
  /** track if up or down */
  private boolean penDown = true;
  
  /** color of ink */
  private Color color = Color.green;
  
  /** width of stroke */
  private int width = 1;
  
  /** list of path segment objects to draw */
  private List<PathSegment> pathSegmentList = new ArrayList<PathSegment>();

  /** list of pixel patch objects to draw */
  private List<PixelPatch> pixelPatchList = new ArrayList<PixelPatch>();

  /** list of ovals objects to draw */
  private List<Oval> ovalList = new ArrayList<Oval>();

  /** list of ovals objects to draw */
  private List<Polygon> polyList = new ArrayList<Polygon>();
  
  //////////////// constructors ///////////////////
  
  /**Constructor that takes no arguments
   */
  public Pen() {}
  
  /**Constructor that takes all the ink color, and width
   * @param color the ink color
   * @param width the width in pixels
   */
  public Pen(Color color, int width) {
    this.color = color;
    this.width = width;
  }
  
  /**Constructor that takes the ink color, width, and penDown flag
   * @param color the ink color
   * @param width the width in pixels
   * @param penDown the flag if the pen is down
   */
  public Pen(Color color, int width, boolean penDown) {
    // use the other constructor to set these
    this(color,width);
    
    // set the pen down flag
    this.penDown = penDown;
  }
  
  ////////////////// methods ///////////////////////
  
  /**Method to get pen down status 
   * @return true if the pen is down else false
   */
  public boolean isPenDown() {return penDown;}
  
  /**Method to set the pen down value
   * @param value the new value to use
   */
  public void setPenDown(boolean value) {penDown = value;}
  
  /**Method to get the pen (ink) color
   * @return the ink color
   */
  public Color getColor() {return color;}
  
  /**Method to set the pen (ink) color
   * @param color the color to use
   */
  public void setColor(Color color) {this.color = color;}
  
  /**Method to get the width of the pen 
   * @return the width in pixels
   */
  public int getWidth() {return width;}
  
  /**Method to set the width of the pen
   * @param width the width to use in pixels
   */
  public void setWidth(int width) {this.width = width;}
  
  /**Method to add a path segment if the pen is down
   * @param x1 the first x
   * @param y1 the first y
   * @param x2 the second x
   * @param y2 the second y
   */
  public synchronized void addMove(int x1, int y1, int x2, int y2) {
    if (penDown) {
      PathSegment pathSeg = new PathSegment(this.color,this.width, new Line2D.Float(x1,y1,x2,y2));
      pathSegmentList.add(pathSeg);
    }
  }

  /**Method to add a pixel part if the pen is down
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public synchronized void colorPixel(int x, int y) {
    if (penDown) {
      PixelPatch pixelPch = new PixelPatch(this.color,1, new Vector2(x, y));
      pixelPatchList.add(pixelPch);
    }
  }

  /**Method to add a pixel part if the pen is down
   * @param x the x coordinate
   * @param y the y coordinate
   * @param heading the heading degrees
   */
  public synchronized void drawPatch(int x, int y, int size, double heading) {
    if (penDown) {
      int sizeHalf = (size/2);
      PixelPatch pixelPch = new PixelPatch(this.color,size, new Vector2(x-sizeHalf, y-sizeHalf), Math.toDegrees(heading));
      pixelPatchList.add(pixelPch);
    }
  }

  /**Method to add an oval if the pen is down
   * @param location the location to draw the shape at
   * @param size the size of the oval
   * @param fill a boolean indicating whether to fill the circle with color or not
   */
  public synchronized void drawOval(Vector2 location, Vector2 size, boolean fill) {
    if (penDown) {
      Oval oval = new Oval(this.color,location, size, fill);
      ovalList.add(oval);
    }
  }
  /**Method to draw a polygon if the pen is down
   * @param points the polygon vertices
   */
  public synchronized void drawPolygon(Vector2[] points, boolean fill) {
    if (penDown) {
      Polygon poly = new Polygon(this.color,this.width,points,fill);
      polyList.add(poly);
    }
  }

  /**Method to draw a quadrilateral if the pen is down
   *  points the polygon vertices
   */
  public synchronized void drawRectangle(Vector2 corner1, Vector2 corner2, boolean fill) {
    if (penDown) {
      Vector2 center = corner1.add(corner2).div(2);
      Vector2 size = corner1.sub(corner2).absolute().div(2);

      Vector2[] points = {
        corner1,
        center.add(size),
        corner2,
        center.sub(size)
      };

      Polygon poly = new Polygon(this.color,this.width,points,fill);
      polyList.add(poly);
    }
  }
  
  /**
   * Method to clear everything drawn
   */
  public void clear() {
    pathSegmentList.clear(); 
    pixelPatchList.clear(); 
    ovalList.clear();
    polyList.clear();
  }
  /**
   * Method to clear the path stored for this pen
   */
  public void clearPath() { 
    pathSegmentList.clear(); 
  }
  /**
   * Method to clear the pixel patch stored for this pen
   */
  public void clearPatch() { 
    pixelPatchList.clear();
  }
  
  /**
   * Metod to paint the pen path 
   * @param g the graphics context
   */
  public synchronized void paintComponent(Graphics g) {

    Color oldcolor = g.getColor();

    // get path segment list
    Iterator<PathSegment> pathIterator = pathSegmentList.iterator();
    
    // loop through path segments
    while (pathIterator.hasNext()) {pathIterator.next().paintComponent(g);}

    // get pixel patch list
    Iterator<PixelPatch> patchIterator = pixelPatchList.iterator();

    // loop through pixel paths
    while (patchIterator.hasNext()) {patchIterator.next().paintComponent(g);};

    // get oval list
    Iterator<Oval> ovalIterator = ovalList.iterator();

    // loop through ovals
    while (ovalIterator.hasNext()) {ovalIterator.next().paintComponent(g);};

    // get oval list
    Iterator<Polygon> polyIterator = polyList.iterator();

    // loop through polygons
    while (polyIterator.hasNext()) {polyIterator.next().paintComponent(g);};

    g.setColor(oldcolor);
  }
  
} // end of class

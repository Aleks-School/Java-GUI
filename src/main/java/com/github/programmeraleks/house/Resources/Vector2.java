package com.github.programmeraleks.house.Resources;

/**
 * A class for performing operations in 2D space
 * @author Aleksandr Stinchcomb
 * @version 1.3
 */
public class Vector2 {
    // Variables
    public double x, y = 0d;

    // Constructors
    public Vector2() {}
    public Vector2(double x) {
        this.x = x;
    }
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2(Vector2 vector) {
        this.x = vector.x;
        this.y = vector.y;
    }
    public Vector2(javafx.geometry.Dimension2D dimension) {
        x = dimension.getWidth();
        y = dimension.getHeight();
    }
    public Vector2(javafx.geometry.Point2D point) {
        x = point.getX();
        y = point.getY();
    }
    public Vector2(Vector2 location, Vector2 lookAt) {
        x = (lookAt.x - location.x);
        y = (lookAt.y - location.y);
    }

    // Public Variables
    final public static Vector2 zero = new Vector2();
    final public static Vector2 one = new Vector2(1,1);
    final public static Vector2 xAxis = new Vector2(1,0);
    final public static Vector2 yAxis = new Vector2(0,1);

    // Methods
    /**Restricts the value range in-between min and max, inclusive
     * @since 1.0
     * @param value the value being clamped
     * @param min the minimum acceptable value
     * @param max the maximum acceptable value
     * @return the value in the range in-between min and max, inclusive
     */
    private static double clamp(double value, double min, double max) {return Math.max(min, Math.min(max, value));}
    /**Calculates the value interpolated/extrapolated from a to b by the given alpha
     * @since 1.0
     * @param a the initial value
     * @param b the target value
     * @param alpha the percentage to interpolate/extrapolate by
     * @return the value interpolated/extrapolated from a to b
     */
    private static double lerp(double a, double b, double alpha) {return a + (b - a) * alpha;}
    /**Calculates the alpha from the given value interpolated/extrapolated from a to b
     * @since 1.0
     * @param a the initial value
     * @param b the target value
     * @param value the value to interpolate/extrapolate from
     * @return the alpha interpolated/extrapolated by value from a to b
     */
    private static double inverseLerp(double a, double b, double value) {return (value - a)/(b - a);}

    public String toString() {return String.format("%s[x=%g, y=%g]", getClass().getName(), x, y);}

    /**Compares the two vectors for equality
     * @since 1.0
     * @param vector the vector being compared for equality
     * @return true if the two vectors are equal and false if they are not
     */
    public boolean equals(Vector2 vector) {return (x == vector.x) && (y == vector.y);}
    /**Compares the vector and point for equality
     * @since 1.0
     * @param point the point being compared for equality
     * @return true if the vector and point are equal and false if they are not
     */
    public boolean equals(javafx.geometry.Point2D point) {return (x == point.getX()) && (y == point.getY());}
    /**Compares the vector and dimension for equality
     * @since 1.0
     * @param dimension the dimension being compared for equality
     * @return true if the vector and dimension are equal and false if they are not
     */
    public boolean equals(javafx.geometry.Dimension2D dimension) {return (x == dimension.getWidth()) && (y == dimension.getHeight());}
    /**Compares the vector coordinates to the provided coordinates
     * @since 1.0
     * @param x the x-coordinate to compare
     * @param y the y-coordinate to compare
     * @return true if they are equal and false if they are not
     */
    public boolean equals(double x, double y) {return (this.x == x) && (this.y == y);}

    /**Adds the two vectors
     * @since 1.0
     * @param vector the vector being added to the first
     */
    public Vector2 add(Vector2 vector) {return new Vector2(x+vector.x, y+vector.y);}
    /**Adds the vector with a constant
     * @since 1.0
     * @param constant the value to add the vector with
     */
    public Vector2 add(double constant) {return new Vector2(x+constant, y+constant);}

    /**Subtracts the two vectors
     * @since 1.0
     * @param vector the vector being subtracted from the first
     */
    public Vector2 sub(Vector2 vector) {return new Vector2(x-vector.x, y-vector.y);}
    /**Subtracts the vector by a constant
     * @since 1.0
     * @param constant the value to subtract the vector by
     */
    public Vector2 sub(double constant) {return new Vector2(x-constant, y-constant);}

    /**Multiplies the two vectors
     * @since 1.0
     * @param vector the vector being multiplied by the first
     */
    public Vector2 mul(Vector2 vector) {return new Vector2(x*vector.x, y*vector.y);}
    /**Multiplies the vector with a constant
     * @since 1.0
     * @param constant the value to multiply the vector by
     */
    public Vector2 mul(double constant) {return new Vector2(x*constant, y*constant);}

    /**Divides the two vectors
     * @since 1.0
     * @param vector the vector being divided from the first
     */
    public Vector2 div(Vector2 vector) {return new Vector2(x/vector.x, y/vector.y);}
    /**Divides the vector by a constant
     * @since 1.0
     * @param constant the value to divide the vector by
     */
    public Vector2 div(double constant) {return new Vector2(x/constant, y/constant);}

    /**Modulo the two vectors
     * @since 1.0
     * @param vector the vector being _ _ the first
     */
    public Vector2 mod(Vector2 vector) {return new Vector2(x%vector.x, y%vector.y);}
    /**Modulo the vector by a constant
     * @since 1.0
     * @param constant the value to modulo the vector by
     */
    public Vector2 mod(double constant) {return new Vector2(x%constant, y%constant);}

    /**Calculates the absolute values of the vector coordinates
     * @since 1.0
     * @return a vector with the absolute values of the vector coordinates
     */
    public Vector2 absolute() {return new Vector2(Math.abs(x), Math.abs(y));}

    /**Calculates the normal vector<p>Transforms the vector into a unit vector with a magnitude of 1
     * @since 1.0
     */
    public Vector2 unit() {
        double magnitude = magnitude();
        if (magnitude != 0d) {return mul(1d / magnitude);} // avoid divide-by-zero error
        return new Vector2(this);
    }

    /**Calculates the length of the vector
     * @since 1.0
     * @return the length of the vector
     */
    public double magnitude() {return Math.sqrt(x*x + y*y);}
    /**Calculates the distance between the two vectors
     * @since 1.0
     * @param vector the vector to find the distance from the first
     * @return the distance between the two vectors
     */
    public double magnitude(Vector2 vector) {return new Vector2(x-vector.x, y-vector.y).magnitude();}
    /**Calculates the distance between the vector and coordinates
     * @since 1.0
     * @param x the positional x-coordinate
     * @param y the positional y-coordinate
     * @return the distance between the two vectors
     */
    public double magnitude(double x, double y) {return new Vector2(this.x-x, this.y-y).magnitude();}

    /**Calculates the vector interpolated from this vector to the target vector by the given alpha
     * @since 1.1
     * @param targetVector the target vector to interpolate towards
     * @param alpha the percentage to interpolate by
     * @return the vector interpolated from this vector and the target vector
     */
    public Vector2 lerp(Vector2 targetVector, double alpha) {return new Vector2(lerp(x, targetVector.x, alpha), lerp(y, targetVector.y, alpha));}
    /**Calculates the alpha from the initial vector to the target vector by this vector
     * @since 1.1
     * @param initialVector the target to interpolate towards
     * @param targetVector the target to interpolate towards
     * @return the alpha for this vector from the initial vector to the target vector
     */
    public Vector2 inverseLerp(Vector2 initialVector, Vector2 targetVector) {return new Vector2(inverseLerp(initialVector.x, targetVector.x, x), inverseLerp(initialVector.y, targetVector.y, y));}

    /**Calculates the dot product of the two vectors
     * @since 1.1
     * @param vector the other vector to calculate the dot product with
     * @return the dot product of the two vectors
     */
    public double dot(Vector2 vector) {return (x*vector.x + y*vector.y);}

    /**Calculates the cross product of the two vectors
     * @since 1.1
     * @param vector the other vector to calculate the cross product with
     * @return the cross product of the two vectors
     */
    public double cross(Vector2 vector) {return (x*vector.y - vector.x*y);}

    /**Calculates the angle between the two vectors
     * @since 1.2
     * @param vector the other vector to calculate the angle in-between
     * @return the angle between the two vectors (in radians)
     */
    public double angleBetween(Vector2 vector) {return Math.acos(clamp(dot(vector), -1, 1));}

    /**Calculates the signed angle between the two vectors
     * @since 1.2
     * @param vector the other vector to calculate the signed angle in-between
     * @return the signed angle between the two vectors (in radians)
     */
    public double angleBetweenSigned(Vector2 vector) {return Math.atan2(x*vector.y - y*vector.x, x*vector.x + y*vector.y);}

    /**Calculates the reflection direction vector
     * @since 1.3
     * @param normal the surface normal to reflect off of
     * @return the direction vector reflected off the surface normal
     */
    public Vector2 reflect(Vector2 normal) {
        double velocityDotProduct = dot(normal);
        Vector2 reflectVector = new Vector2(x - 2 * velocityDotProduct * normal.x, y - 2 * velocityDotProduct * normal.y);
        return reflectVector;
    }
}
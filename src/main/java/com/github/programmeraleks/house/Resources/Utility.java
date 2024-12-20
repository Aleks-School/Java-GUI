package com.github.programmeraleks.house.Resources;

public class Utility {
    /**Restricts the {@code value} in-between {@code min} and {@code max}, inclusive
     * @param value the value to project to range
     * @param min the minimum acceptable value
     * @param max the maximum acceptable value
     * @return the value within the range from {@code min} to {@code max}, inclusive
     */
    public static double clamp(double value, double min, double max) {
        return (value < min) ? min : (value > max) ? max : value;
    }

    /**Calculates the value interpolated from {@code origin} to {@code target} by {@code alpha}
     * @param origin the value to interpolate from
     * @param target the value to interpolate to
     * @param alpha the percentage to interpolate to {@code target} by
     * @return the value interpolated from {@code origin} to {@code target} by {@code alpha}
     */
    public static double lerp(double origin, double target, double alpha) {
        return origin + (target - origin) * alpha;
    }

    /**Calculates the alpha for {@code value} extrapolated from {@code t1} to {@code t2}
     * @param t1 the target value extrapolated from
     * @param t2 the target value extrapolated to
     * @param value the value to find the alpha of
     * @return the alpha of the {@code value} extrapolated from {@code t1} to {@code t2}
     */
    public static double invLerp(double t1, double t2, double value) {
        return (value - t1) / (t2 - t1);
    }

    /**Calculates the value interpolated from {@code tMin} to {@code tMax} by {@code value}'s alpha extrapolated from {@code min} to {@code max}
     * @param min the current minimum value
     * @param max the current maximum value
     * @param tMin the target minimum value
     * @param tMax the target maximum value
     * @param value the value
     * @return the value interpolated from {@code tMin} to {@code tMax} by {@code value}'s alpha extrapolated from {@code min} to {@code max}
     */
    public static double map(double min, double max, double tMin, double tMax, double value) {
        return lerp(tMin, tMax, invLerp(min, max, value));
    }
}

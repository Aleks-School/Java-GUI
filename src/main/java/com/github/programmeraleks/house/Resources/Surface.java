package com.github.programmeraleks.house.Resources;

public class Surface {
    public Vector2 position;
    public int size;
    public double heading = 0;
    public Surface(Vector2 position, int size, double heading) {
        this.position = position;
        this.size = Math.abs(size);
        this.heading = heading;
    }

    private Vector2 getPosition(Vector2 position) {
        double heading = Math.toRadians(this.heading);
        return new Vector2(Math.cos(heading)*this.position.x - Math.sin(heading)*this.position.y, Math.sin(heading)*this.position.x + Math.cos(heading)*this.position.y);
    }

    public Vector2 getNormal(Vector2 position) {
        int halfSize = Math.abs(size)/2;

        Vector2 topPos = getPosition(this.position.sub(new Vector2(0,halfSize)));
        Vector2 bottomPos = getPosition(this.position.add(new Vector2(0,halfSize)));

        double slope = (topPos.y - bottomPos.y)/(topPos.x - bottomPos.x);
        double perpSlope = (-1d/slope);

        double s = (topPos.y - bottomPos.y) * position.x + (topPos.x - bottomPos.x) * position.y + (bottomPos.x * bottomPos.y - topPos.x * topPos.y);
        if (s < 0) {
            return new Vector2(1,slope).unit();
        } else if (s > 0) {
            return new Vector2(1,perpSlope).unit();
        } else {
            return new Vector2(1,slope).unit();
        }
    }

    public Vector2 intersect(Vector2 position) {
        double x = (this.position.y - position.y) / (position.x - this.position.x);
        double y = (position.x * x + position.y);

        return new Vector2(x, y);
    }
}

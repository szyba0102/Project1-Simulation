package agh.ics.oop;

import java.util.Objects;

public class Vector2d {

    private final int x; // first coord
    private final int y; // second coord

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x,this.y+other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.x,this.y-other.y);
    }

    public boolean equals(Object other){
        if (!(other instanceof Vector2d that))
            return false;
        return this.x == that.x && this.y == that.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public int compareByXY(Vector2d other) {
        if (this.x == other.x) {
            return Integer.compare(this.y, other.y);
        }
        else if (this.x > other.x) return 1;
        else return -1;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

package rl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Line implements Iterable<Point>{

    private List<Point> points;

    //possibly move all this out of the constructor

    // determine the line of sight by using Bresenham's line algorithm
    public Line(int x0, int y0, int x1, int y1){
        points = new ArrayList<Point>();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;


        while (true) {
            points.add(new Point(x0, y0, 0));

            if (x0 == x1 && y0 == y1){
                break;

            } //if

            int e2 = err * 2;
            if (e2 > -dx){
                err -= dy;
                x0 += sx;

            } //if

            if (e2 < dx){
                err += dx;
                y0 += sy;

            } //if


        } //while



    } //Line


    public List<Point> getPoints() {
        return points;
    } //getPoints


    @Override
    public Iterator<Point> iterator() {
        return points.iterator();

    } //iterator

} //class Line

package particleCollision;

public class Circle  {
    float x, y , r;
    float R_squared;
    Circle(float x, float y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
        this.R_squared = this.r * this.r;
    }

    public boolean contains(Point point){
        // check if the point is in the circle by checking if the euclidean distance of
        // the point and the center of the circle if smaller or equal to the radius of
        // the circle
        float distance = (float) (Math.pow((point.x - this.x), 2) + Math.pow((point.y - this.y), 2));
        return distance<= this.R_squared;
    }
    public boolean intersects(Rectangle range){
        float xDist = (float) Math.abs(range.x - this.x);
        float yDist = (float) Math.abs(range.y - this.y);

        // radius of the circle
        float r = this.r;

        float w = (float) range.width;
        float h = (float) range.height;

        float edges = (float) (Math.pow((xDist - w), 2) + Math.pow((yDist - h), 2));
        // no intersection
        if (xDist > (r + w) || yDist > (r + h))
            return false;

        // intersection within the circle
        if (xDist <= w || yDist <= h)
            return true;

        // intersection on the edge of the circle
        return edges <= this.R_squared;
    }


}

package gravitationalAttraction;


public class Rectangle {
    double x, y, width, height;
    Rectangle(double x , double y, double width, double height){
        this.x = x ;
        this.y = y;
        this. width = width;
        this. height = height;
    }

    public boolean intersects( Rectangle range) {
        return !(range.x - range.width> this.x + this.width||
                range.x + range.width< this.x - this.width||
                range.y - range.height> this.y + this.height||
                range.y + range.height< this.y - this.height);
    }
    public boolean contains (Point p){
        return  p.x <= this.x+this.width &&
                p.x >= this.x-this.width &&
                p.y <= this.y+this.height &&
                p.y >= this.y-this.height ;
    }
}

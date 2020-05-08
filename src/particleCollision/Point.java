package particleCollision;

public class Point {
    double x , y;
    Particle userdata;
    Point(double x , double y, Particle userdata){
        this.x = x;
        this.y = y;
        this.userdata = userdata;
    }
    // Getters and setters
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
}

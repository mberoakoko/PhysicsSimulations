package gravitationalAttraction;

import particleCollision.Particle;
import processing.core.PVector;

public class Point {
    double x , y ;
    PVector position;
    PVector velocity = new PVector((float)Math.random(), (float)Math.random());
    sketch p5;
    PVector acceleration;

    float mass;
    Point(sketch _p5, double x , double y){
        this.x = x;
        this.y = y;
        this.p5 = _p5;
        this.position = new PVector((float) this.x, (float) this.y);
        this.mass = 1F;
        this.acceleration = new PVector(this.p5.random(-0.01F, 0.1F), this.p5.random(-0.1F, 0.1F));
    }
    // Getters and setters
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getMass() { return this.mass; }

    private  void checkBorders(){
        int width = this.p5.width, height = this.p5.height;
        float X_position = (float) this.x, Y_position = (float)this.y;
        if(X_position <= 0 || X_position>=width){
            this.velocity.x = this.velocity.x * -1;
        }
        if(Y_position <= 0 || Y_position>=height){
            this.velocity.y = this.velocity.y * -1;
        }
        if (X_position <= 0) {
            this.position.x = 0;
        }else if(X_position >= width){
            this.position.x = width;
        }
        if (Y_position <= 0) {
            this.position.y = 0;
        }else if(Y_position >= height){
            this.position.y = height;
        }
    }

    public void move(){
        checkBorders();
        this. velocity = this.velocity.add(this.acceleration);
        this.position = this.position.add(this.velocity);
        this.x = this.position.x;
        this.y = this.position.y;
    }
    public void applyForce(PVector force){
        PVector newAcceleration = force.mult(this.mass);
        this.acceleration = this.acceleration.add(newAcceleration);
    }


    private PVector gravitationalFunction(Point current, Point target){
        float distance = (float) Math.pow(Math.pow((target.x - current.x), 2)
                - Math.pow((target.y - current.y), 2), 0.5);
         float GRAVITATIONALCONSTANT = (float) ((float) 6.67408 * Math.pow(10, -11));
         PVector direction = new PVector((float) (target.x - current.x), (float) (target.x - current.x));
         PVector force = new PVector(1, 1);
         return force.mult(((-GRAVITATIONALCONSTANT*current.mass/distance))).mult(target.mass);
    }
    public PVector forceDueToGravitation(Quadtree tree){
        if(tree != null){
            if(tree.points.size() != 0){
                PVector force = new PVector(0, 0);
                if(tree.theta < 0.5){
                    Point nodePoint = new Point(this.p5,tree.centerOfMass.x, tree.centerOfMass.y);
                    nodePoint.mass = tree.totalMass;
                    force = gravitationalFunction(this, nodePoint);
                    return force;
                }
                else {
                    force = force.add(forceDueToGravitation(tree.northeast)).add(forceDueToGravitation(tree.northwest))
                            .add(forceDueToGravitation(tree.southeast)).add(forceDueToGravitation(tree.southwest));
                    return force;
                }
            }
        }
        return new PVector(0, 0);
    }




    public void render(){
        p5.stroke(255);
        p5.strokeWeight(1);
        p5.noFill();
        p5.rectMode(p5.CENTER);
        p5.point((float) this.x, (float) this.y);
    }

}
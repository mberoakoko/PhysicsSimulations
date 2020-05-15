package gravitationalAttraction;

import particleCollision.Particle;
import processing.core.PApplet;
import processing.core.PVector;

public class Point {
    double x , y ;
    PVector position;
    PVector velocity ;
    sketch p5;
    PVector acceleration;

    float mass;
    Point(sketch _p5, double x , double y){
        this.x = x;
        this.y = y;
        this.p5 = _p5;
        this.position = new PVector((float) this.x, (float) this.y);
        this.mass = this.p5.random(100, 1000);
        this.velocity = new PVector(0, 0);
        this.acceleration = new PVector(0, 0);
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
        int width = this.p5.width*5, height = this.p5.height*5;
        float X_position = (float) this.x, Y_position = (float)this.y;
        if(X_position <= 0 || X_position>=width){
            this.velocity.x = this.velocity.x * -0.1f;
        }
        if(Y_position <= 0 || Y_position>=height){
            this.velocity.y = this.velocity.y * -0.1f;
        }
        if (X_position <= -(5/2)*width) {
            this.position.x = -(5/2)*width;
        }else if(X_position >= (5/2)*width){
            this.position.x = (5/2)*width;
        }
        if (Y_position <= -(5/2)*height) {
            this.position.y = -(5/2)*height;
        }else if(Y_position >= (5/2)*height){
            this.position.y = (5/2)*height;
        }
    }



    /**
     * Moves and Renders the the individual body
     * @param tree
     */
    public void move(Quadtree tree){
        //checkBorders();
        PVector forceApplied = forceDueToGravitation(tree);
        applyForce(forceApplied);
        this. velocity = this.velocity.add(this.acceleration);
        this.acceleration = this.acceleration.mult(0);
        this.position = this.position.add(this.velocity);
        this.x = this.position.x;
        this.y = this.position.y;
    }
    public void applyForce(PVector force){
        PVector newAcceleration = force.mult(this.mass);
        this.acceleration = this.acceleration.add(newAcceleration);
    }



    /**
     * Calculates the gravitational force of two bodies
     * @param current
     * @param target
     * @return direction
     */
    public PVector gravitationalFunction(Point current, Point target){
        float distance = (float) Math.pow(Math.pow((target.x - current.x), 2)
                + Math.pow((target.y - current.y), 2), 0.5);
         float GRAVITATIONALCONSTANT = (float) ((float) 6.67408 * Math.pow(10, -11));
         PVector direction = new PVector((float) (target.x - current.x), (float) (target.y - current.y));
         direction = direction.normalize();
         return direction.mult(((GRAVITATIONALCONSTANT*current.mass/distance))).mult(target.mass);
    }

    /**
     * Returns the net force due to Gravitation, experienced by the Body
     * @param tree
     * @return force
     */
    public PVector forceDueToGravitation(Quadtree tree){
        PVector force = new PVector(0 , 0);
        if(tree != null){
            if(tree.points.size() != 0){
                if(tree.theta < 3 ){
                    //System.out.println("Am Working");
                    Point nodePoint = new Point(this.p5,tree.centerOfMass.x, tree.centerOfMass.y);
                    nodePoint.mass = tree.totalMass;
                    force = gravitationalFunction(this, nodePoint);
                    return force;
                }
                else {
                    force = force.add(forceDueToGravitation(tree.northeast)).add(forceDueToGravitation(tree.northwest))
                            .add(forceDueToGravitation(tree.southeast)).add(forceDueToGravitation(tree.southwest));
                }
                return force;
            }
        }
        return new PVector(0, 0);
    }

    public PVector netGravitationalForce(Quadtree tree){
        PVector force = new PVector(0, 0);
        if(tree == null ){
            return new PVector(0, 0);
        }
        if(tree.points.size() != 0){
            if(!tree.divide && tree.points.get(0) != this){
                force.add(gravitationalFunction(this, tree.points.get(0)));
                //System.out.println("Calculated force is ->" + force);
                return force;
            }else if(tree.divide){
                //
                if(tree.theta < 1){
                    Point nodePoint = new Point(this.p5,tree.centerOfMass.x, tree.centerOfMass.y);
                    nodePoint.mass = tree.totalMass;
                    force = force.add(gravitationalFunction(this, nodePoint));
                    // Some forces are NAN
                    return force;
                }else if (tree.theta > 1){
                    force.add(netGravitationalForce(tree.northeast));
                    force.add(netGravitationalForce(tree.southwest));
                    force.add(netGravitationalForce(tree.northwest));
                    force.add(netGravitationalForce(tree.southeast));

                    return force;
                }

            }
            return force;

        }
        return force;

    }


    public void render(){
        float color = PApplet.map(this.mass, 100, 1000, 0, 255);
        p5.fill(255,255, 255);
        p5.noStroke();
        p5.rectMode(p5.CENTER);
        p5.ellipse((float) this.x, (float) this.y, 2, 2);
    }

}
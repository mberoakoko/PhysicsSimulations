package viscousDrag;
import processing.core.PVector;

public class Particle {
    sketch p5;
    float x, y , r = 4.0F, mass;
    private boolean highlight = false;
    PVector velocity ;
    PVector acceleration;
    PVector position ;
    float FRICTION_COEFFICIENT;
    Particle(sketch _p5, float x, float y, float mass, float frictionCoefficient){
        this.x = x ;
        this.y = y;
        this.FRICTION_COEFFICIENT = frictionCoefficient;
        this.p5 = _p5;
        this.mass = mass;
        this.position = new PVector(this.x, this.y);
        this.velocity = new PVector(0, 0);
        this.acceleration = new PVector(0,0);
    }

    private void checkBorders(){
        int width = this.p5.width;
        int height = this.p5.height;
        if(this.position.x < 0 || this.position.x > width){// Check on the x  vector
            this.velocity.x = this.velocity.x * (float)-0.9;
        }
        if(this.position.y < 0 || this.position.y > height){// Check on the x  vector
            this.velocity.y = this.velocity.y * (float)-0.9;
            this.position.y = height;
        }
        /*System.out.println("Particle at position => ( "+ this.position.x+ " : " + this.position.y+ " .");
        System.out.println("Particle at Velocity => ( " + this.velocity + " .");*/
    }

    private float distance(float x1,float x2, float y1, float y2){
        return (float)Math.pow(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2), 0.5);
    }
    void applyForce(PVector force){
        PVector ForceDueAcceleration = PVector.div(force, this.mass);
        this.acceleration.add(ForceDueAcceleration);
    }
    public void move(){
        // This function moves the particles
        checkBorders();
        this.velocity = this.velocity.add(this.acceleration);
        this.position = this.position.add(this.velocity);
        this.acceleration.mult(0);
    }

    public boolean intersects(Particle other){
        float d = distance(this.position.x, other.x, this.position.y, other.y);
        return (d< this.r + other.r);
    }

    public void setHighlight(boolean value){
        this.highlight = value;
    }
    public void Render(){
        p5.stroke(0);
        p5.strokeWeight(2);
        if(this.highlight){
            p5.fill(255);
        }else {
            p5.fill(255, 127);
        }

        p5.ellipse(this.position.x , this.position.y , this.r * 5, this.r * 5);
    }
}

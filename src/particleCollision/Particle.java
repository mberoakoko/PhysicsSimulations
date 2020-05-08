package particleCollision;
import processing.core.PVector;
public class Particle extends sketch {
    sketch p5;
    float x, y , r = 4.0F;
    private boolean highlight = false;
    PVector velocity ;
    PVector acceleration;
    PVector position ;
    Particle(sketch _p5, float x,float y){
        this.x = x ;
        this.y = y;
        this.p5 = _p5;
        this.position = new PVector(this.x, this.y);
        this.velocity = new PVector(p5.random((float)-0.5, (float)0.5),
                p5.random((float)-0.5, (float)0.5));
        this.acceleration = new PVector(p5.random((float)0.001),p5.random((float)0.0001));
    }

    private void checkBorders(){
        int width = this.p5.width;
        int height = this.p5.height;
        if(this.position.x < 0 || this.position.x > width){// Check on the x  vector
            this.velocity.x = this.velocity.x * (float)-0.99;
        }
        if(this.position.y < 0 || this.position.x > height){// Check on the x  vector
            this.velocity.y = this.velocity.y * (float)-0.99;
        }
    }

    private float distance(float x1,float x2, float y1, float y2){
        return (float)Math.pow(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2), 0.5);
    }

    public void move(){
        // This function moves the particles
        checkBorders();
        this.position = this.position.add(this.velocity);
    }

    public boolean intersects(Particle other){
        float d = distance(this.position.x, other.x, this.position.y, other.y);
        return (d< this.r + other.r);
    }

    public void setHighlight(boolean value){
        this.highlight = value;
    }
    public void Render(){
        p5.noStroke();
        if(this.highlight){
            p5.fill(255);
        }else {
            p5.fill(100);
        }

        p5.ellipse(this.position.x , this.position.y , this.r * 2, this.r * 2);
    }
}

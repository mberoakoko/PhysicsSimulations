package viscousDrag;

import processing.core.PVector;

public class Liquid {
    // Has two main functions, calculate the drag that would act on an
    // an object and detect if the particle is actually in a liquid
    int x, y, width, height;
    float density;
    sketch p5;
    Liquid(sketch _p5, int x, int y, int width, int height, float density){
        this.x = x;
        this.y = y;
        this.p5 = _p5;
        this.width = width;
        this.height = height;
        this.density = density;
    }
    public boolean contains(Particle p){
        return p.position.x>this.x && p.position.x < this.x + this.width
                && p.position.y>this.y && p.position.y<this.y + this.height;
    }
    public PVector calculateDrag(Particle p){
        PVector drag ;
        float speed = p.velocity.mag();
        float area = (float) Math.PI * (p.r*p.r);
        float dragMagnitude = (float) 0.5 * p.FRICTION_COEFFICIENT * area * density * speed * speed;
        drag = p.velocity.mult(-1);
        drag = drag.normalize();
        drag = drag.mult(dragMagnitude);
        System.out.println(drag);
        return  drag;
    }

    void render(){
        p5.noStroke();
        p5.fill(173, 216, 230);
        p5.rect(this.x, this.y, this.width, this.height);
    }
}

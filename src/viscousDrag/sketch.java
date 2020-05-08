package viscousDrag;

import processing.core.PApplet;
import processing.core.PVector;

public class sketch extends PApplet {
    int height = 600, width = 800;
    Liquid liquid;
    PVector gravity = new PVector(0, 9F);
    Particle p;
    int PositionX = 400, positionY = 0;
    public void settings(){
        size(width, height);
        liquid = new Liquid(this, 0, 400, width, height -400, 0.000000000001F);
        p = new Particle(this, PositionX,  positionY, 100, 0.0045F);
    }
    public void setup(){}
    public void draw(){
        background(255, 236, 236);
        liquid.render();
        if(liquid.contains(p)){
            PVector drag = liquid.calculateDrag(p);
            p.applyForce(drag);
        }
        p.applyForce(gravity);
        p.Render();
        p.move();


    }
    public void keyReleased(){
        if (key == BACKSPACE){
            p.position.x =PositionX;
            p.position.y = positionY;
        }
    }

    public static void main(String[] args) {
        String [] processingArgs = {"Viscous Drag"};
        sketch Sketch = new sketch();
        PApplet.runSketch(processingArgs, Sketch);
    }
}

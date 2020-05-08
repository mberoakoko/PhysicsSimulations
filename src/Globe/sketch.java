package Globe;

import peasy.PeasyCam;
import processing.core.PApplet;

public class sketch extends PApplet {
    PeasyCam cam;
    Sphere sphere;
    public void setup(){
        cam = new PeasyCam(this, 1800);
        sphere = new Sphere(this, 1000, 100);
        sphere.init();
    }

    public void settings() {
        size(800, 600, P3D);
    }

    public void draw() {
        background(0);
        fill(255);
        //lights();
        sphere.generateSphere();
    }
    public static void main(String[] args) {
        String [] processingArgs = {"Generated Globe"};
        sketch Sketch = new sketch();
        PApplet.runSketch(processingArgs, Sketch);
    }
}

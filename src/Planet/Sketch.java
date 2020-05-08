package Planet;

import processing.core.PApplet;

public class Sketch extends PApplet {
    Planet sun;
    int width = 600, height = 600;
    public void settings(){
        size(width, height);
        sun = new Planet(this, 50, 0);
        sun.spawnMoons(1, 1);
    }
    public void draw(){
        background(0);
        translate(width/2, height/2);
        sun.show();
        sun.orbit();
    }
    public static void main(String[] args) {
        String [] processingArgs = {"Planetary System"};
        Sketch sketch = new Sketch();
        PApplet.runSketch(processingArgs, sketch);
    }
}

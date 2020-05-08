package fluidMechanics2D;

import javafx.scene.transform.Scale;
import processing.core.PApplet;

public class Sketch extends PApplet {
    Fluid fluid;
    final int N = 128;
    final int SCALE = 4;
    int v1, v2, v3;
    public void settings(){
        size(N* SCALE, N*SCALE);
    }
    public void setup(){
        fluid = new Fluid( this, 0.9F,0, 0.000001F);
    }
    public void draw(){
        background(0);
        fluid.step();
        fluid.renderDensity(255, 7,255);
        fluid.fadeD();
    }

    public void mouseDragged(){
        fluid.addDensity(mouseX/SCALE, mouseY/SCALE, 100);
        float amtX = mouseX - pmouseX;
        float amtY = mouseY - pmouseY;
        v1 = (int)random(0, 255);
        v2 = (int)random(0, 255);
        v3 = (int)random(0, 255);
        fluid.addVelocity(mouseX/SCALE, mouseY/SCALE, amtX, amtY);
    }

    public static void main(String[] args) {
        String [] ProcessingArgs = {"Fluid Flow"};
        Sketch mySketch = new Sketch();
        PApplet.runSketch(ProcessingArgs, mySketch);
    }
}

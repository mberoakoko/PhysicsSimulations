package particleCollision;

import processing.core.PApplet;

import java.nio.channels.FileLock;
import java.util.ArrayList;

public class sketch extends PApplet {
    int width = 800, height = 800;
    Quadtree Qtree;
    Particle [] particles;
    int particleNumber = 200;
    public void settings(){
        size(width, height);
        particles = new Particle[particleNumber];
        for(int i = 0; i<particleNumber; i++){
            particles[i] = new Particle(this, 400, 400);
        }
    }

    public void draw(){
        background(0);
        if(keyPressed){
            if(key == ENTER){
                particleNumber +=10;
            }
            if(key == BACKSPACE){
                particleNumber -= 10;
            }
        }
        Rectangle boundary = new Rectangle((float)width/2 , (float)height/2, width, height);
        Qtree = new Quadtree(this, boundary, 1);
        for(Particle p : particles){
            Point point = new Point(p.position.x, p.position.y, p);
            Qtree.insert(point);
            p.move();
            p.Render();
            p.setHighlight(false);
        }
        ArrayList<Point> temp = new ArrayList<>();
        for(Particle p : particles){
            Rectangle range = new Rectangle(p.x-p.r, p.y-p.r, p.r*2, p.r*2);
            ArrayList<Point> points = Qtree.queryRectangle(range, temp);
            for(Point point : points){
                Particle other = point.userdata;
                if(p != other && p.intersects(other)){
                    p.setHighlight(true);
                }
            }
        }
        Qtree.show();


        System.out.println(frameRate);
    }


    public static void main(String[] args) {
        String [] processingArgs  = {"Simulated Collisions"};
        sketch sketch = new sketch();
        PApplet.runSketch(processingArgs, sketch);
    }
    public float randomGaussian(float min , float max){
        return min + randomGaussian() * (max - min);
    }
}

package gravitationalAttraction;

import processing.core.PApplet;

public class sketch extends PApplet {
    Quadtree Quadtree;
    int width = 600, height = 600;
    Rectangle boundary;
    int count = 0;
    Point [] point;
    public void settings(){
        size(width, height);
        point = new Point[10];
        for(int i = 0; i<10; i++){
            point[i]= new Point(this, random(0, width), random(0, height));
        }

    }
    public void draw(){
        background(0);
        boundary = new Rectangle((float)width/2 , (float)height/2, width, height);
        Quadtree = new Quadtree(this, boundary, 1);
        for(Point p : point){
            Quadtree.insert(p);
            count++;
            if(count == 10){
                p.applyForce(p.forceDueToGravitation(Quadtree));
                p.move();
                p.render();
            }
        }
        Quadtree.initializeTree();
    }

    public void printTheta(Quadtree tree){
        if(tree != null){
            printTheta(tree.northwest);
            printTheta(tree.northeast);
            if(tree.theta<0.5 && tree.theta>0){
                count++;
                System.out.println("We have a bingo!!! " + count);
            }
            printTheta(tree.southwest);
            printTheta(tree.southeast);
        }
    }

    public static void main(String[] args) {
        String [] processingArgs = {"Gravitational Attraction"};
        sketch Sketch = new sketch();
        PApplet.runSketch(processingArgs, Sketch);

    }
}

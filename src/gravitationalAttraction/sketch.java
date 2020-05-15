package gravitationalAttraction;

import processing.core.PApplet;
import processing.core.PVector;

public class sketch extends PApplet {
    Quadtree Quadtree;
    int width = 1200, height = 700;
    Rectangle boundary;
    int count = 0;
    int PARTICLEQUANTITY = 3500;
    Point [] point;
    Point testPoint = new Point(this, random(width), random(height));
    public void settings(){
        size(width, height);
        point = new Point[PARTICLEQUANTITY];
        for(int i = 0; i<PARTICLEQUANTITY; i++){
            point[i]= new Point(this, random(400, 800), random(150, 550));
        }

        testPoint.mass = 5000;
        //testParams();

    }
    public void draw(){
        background(0);
       boundary = new Rectangle((float)(width/2) , (float)height/2, width*5, height*5);
        Quadtree = new Quadtree(this, boundary, 1);
        for(Point p : point){
            Quadtree.insert(p);
        }
        Quadtree.initializeTree();
        for(Point p : point){
            p.render();
             p.move(Quadtree);

        }
        //Quadtree.show();
       //bruteForce();
    }

    public void printTheta(Quadtree tree){
        if(tree != null){
            if(!tree.divide){
                count++;
                System.out.println("The Corresponding width is:=>" + tree.centerOfMass);
                System.out.println("Total ping is " + count);
            }
            printTheta(tree.northwest);
            printTheta(tree.northeast);
            printTheta(tree.southwest);
            printTheta(tree.southeast);
        }
    }
    public void testParams(){
        boundary = new Rectangle((float)width/2 , (float)height/2, width, height);
        Quadtree = new Quadtree(this, boundary, 1);
        for(Point p : point){
            Quadtree.insert(p);
        }
        Quadtree.initializeTree();
        for(Point p : point){
            //p.move(Quadtree);
            //p.render();
        }
        //Quadtree.show();
        System.out.println("The tree center of mass is :-> " + Quadtree.theta);
        printTheta(Quadtree);
    }
    public void bruteForce(){
        for(int i = 0; i< PARTICLEQUANTITY; i++){
            for(int j = 0; j< PARTICLEQUANTITY; j++){
                if(i == j){
                    break;
                }
                PVector force = point[i].gravitationalFunction(point[i], point[j]);
                point[i].applyForce(force);
                point[i].move(Quadtree);
                point[i].render();
            }
        }
    }

    public static void main(String[] args) {
        String [] processingArgs = {"Gravitational Attraction"};
        sketch Sketch = new sketch();
        PApplet.runSketch(processingArgs, Sketch);

    }
}

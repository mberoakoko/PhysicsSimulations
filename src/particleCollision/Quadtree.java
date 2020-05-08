package particleCollision;

import java.util.ArrayList;

public class Quadtree extends sketch {
    Rectangle boundry;
    int capacity; // max no. of points
    ArrayList <Point> points ;
    boolean divide = false;
    Quadtree northeast;
    Quadtree northwest;
    Quadtree southeast;
    Quadtree southwest;
    sketch p5;

    public Quadtree(sketch _p5, Rectangle rect , int cap){
        this.boundry = rect;
        this.capacity = cap;
        this.p5 = _p5;
        points = new ArrayList<Point>();
    }

    public boolean insert (Point p){
        if (!this.boundry.contains(p)) return false; //<>//
        if (this.points.size() < this.capacity ){
            this. points.add(p);
            return true;
        }
        else {
            if (!this.divide) {
                subDivide();
            }
            if (this.northeast.insert(p)){
                return true;
            }
            else if (this.northwest.insert(p)){
                return true;
            }
            else if (this.southeast.insert(p)){
                    return true;
                }
            else if (this.southwest.insert(p)){
                return true;
            }
            else return false;
        }
    }
    public void  subDivide (){
        double x = this.boundry.x;
        double y = this.boundry.y;
        double w = this.boundry.width;
        double h = this.boundry.height;
        this.northeast = new Quadtree(this.p5, new Rectangle (x+w/2 , y-h/2 , w/2 , h/2),capacity);
        this.northwest = new Quadtree(this.p5, new Rectangle (x-w/2 , y-h/2 , w/2 , h/2),capacity);
        this.southeast = new Quadtree(this.p5, new Rectangle (x+w/2 , y+h/2 , w/2 , h/2),capacity);
        this.southwest = new Quadtree(this.p5, new Rectangle (x-w/2 , y+h/2 , w/2 , h/2),capacity);
        this.divide = true;
    }
    public ArrayList <Point>  queryRectangle (Rectangle range,ArrayList <Point> found){
        if(found == null){
            found = new ArrayList<>();
        }
        if(!this.boundry.intersects(range)){
            return  found;
        }else {
            for(Point p: this.points){
                if(range.contains(p)){
                    found.add(p);
                }
            }
            if(this.divide){
                this.northwest.queryRectangle(range, found);
                this.northeast.queryRectangle(range, found);
                this.southwest.queryRectangle(range, found);
                this.southeast.queryRectangle(range, found);
            }
        }
        return found;
    }

    /* public ArrayList <Point>  queryCircle (Circle range,ArrayList <Point> found){
         if(found == null){
             found = new ArrayList<>();
         }
         if(!this.boundry.intersects(range)){
             return  found;
         }else {
             for(Point p: this.points){
                 if(range.contains(p)){
                     found.add(p);
                 }
             }
             if(this.divide){
                 this.northwest.queryCircle(range, found);
                 this.northeast.queryCircle(range, found);
                 this.southwest.queryCircle(range, found);
                 this.southeast.queryCircle(range, found);
             }
         }
         return found;
     }
 */
    public void show (){
        p5.stroke(255);
        p5.strokeWeight(1);
        p5.noFill();
        p5.rectMode(p5.CENTER);
        p5.rect ((float)this.boundry.x , (float)this.boundry.y ,
                (float)this.boundry.width *2 , (float)this.boundry.height*2);
        if (this.divide){
            this.northeast.show();
            this.northwest.show();
            this.southeast.show();
            this.southwest.show();
        }
        // (for showing points)
        for (Point p:points){
            p5.strokeWeight(2);
            p5.stroke(255);
            p5.point((float)p.x ,(float) p.y);
        }
    }

}

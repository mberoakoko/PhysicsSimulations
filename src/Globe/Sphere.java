package Globe;

import processing.core.PApplet;
import processing.core.PVector;

public class Sphere {

    float radius;
    int resolution;
    PVector [][] globe;
    sketch p5;
    Sphere(sketch _p5, float radius, int resolution){
        this.p5 = _p5;
        this.radius = radius;
        this.resolution = resolution;
        this.globe = new PVector[this.resolution+1][this.resolution+1];
    }
    void init(){
        generateMap();
    }
    void generateMap(){
        float [][] noiseMap = new float[this.resolution+1][this.resolution+1];
        float yOffset = 0;
        for(int i = 0 ; i<=this.resolution; i++){
            float xOffset = 0;
            for(int j = 0; j<= this.resolution ; j++){
                noiseMap[i][j] = PApplet.map(p5.noise(xOffset, yOffset), 0, 1, 1, 1.2f);
                xOffset += 0.1;
                System.out.println(noiseMap[i][j]);
            }
            yOffset +=0.1;
        }
        for(int i = 0; i<=this.resolution ; i++){
            float longitude = PApplet.map(i, 0, this.resolution, -p5.PI, p5.PI);
            for(int j = 0; j<=this.resolution ; j++){
                float latitude = PApplet.map(j, 0, this.resolution, -p5.HALF_PI, p5.HALF_PI);
                float x = (float) (this.radius * Math.sin((double)longitude) * Math.cos((double)latitude));
                float y = (float) (this.radius * Math.sin((double)longitude) * Math.sin((double)latitude));
                float z = (float) (this.radius * Math.cos(longitude));
                this.globe[i][j] = new PVector(x, y, z);
                this.globe[i][j].mult(noiseMap[i][j]);
                System.out.println(this.globe[i][j]);
            }
        }
    }
    void generateSphere(){

        for (int i = 0; i < this.resolution; i++) {
            p5.stroke(255);
            //p5.rotateX(p5.PI/20);
            p5.noFill();
            p5.beginShape(p5.TRIANGLE_STRIP);
            for (int j = 0; j < this.resolution+1; j++) {
                PVector v = this.globe[i][j];
                p5.strokeWeight(1);
                p5.vertex(v.x, v.y, v.z);
                PVector v2 = this.globe[i+1][j];
                p5.vertex(v2.x, v2.y, v2.z);
            }
            p5.endShape();
        }

    }
}

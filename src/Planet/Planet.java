package Planet;

public class Planet {
    float radius;
    float distance;
    Planet[] planets;
    Sketch p5;
    Planet(Sketch _p5, float r, float d) {
        this.radius = r;
        this.distance = d;

        this.p5 = _p5;
    }
    void orbit(){

    }
    void spawnMoons(int total , int level){
        planets = new Planet[total];
        for(int i =0; i<planets.length ;i++){
            float r = this.radius/(level/2F);
            float d = p5.random(50, 150);
            planets[i] = new Planet(this.p5, r, d/level);
            if (level < 3) {
                int num = (int)p5.random(0,4);
                planets[i].spawnMoons(num, level+1);
            }
        }
    }
    void show() {
        p5.pushMatrix();
        p5.fill(255, 100);
        p5.translate(this.distance, 0);
        p5.ellipse(0, 0, this.radius*2, this.radius*2);
        if (planets != null) {
            for (Planet planet : planets) {
                planet.show();
            }
        }
        p5.popMatrix();
    }
}

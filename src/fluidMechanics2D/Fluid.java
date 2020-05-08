package fluidMechanics2D;

import processing.core.PApplet;

public class Fluid {
    int size;
    int N = 128;
    float dt ;
    float diff;
    float viscosity;

    float [] s;
    float [] density;

    float [] vx;
    float [] vy;

    float [] vx0;
    float [] vy0;

    Sketch p5;

    int iter = 16;
    Fluid(Sketch _p5,float dt, float diffusion, float viscosity){
        this.p5 = _p5;
        this.size = N;
        this.dt = dt;
        this.diff  = diffusion;
        this.viscosity = viscosity;

        this.s = new float[N*N];
        this.density = new float[N*N];

        this.vx = new float[N*N];
        this.vy = new float[N*N];

        this.vx0 = new float[N*N];
        this.vy0 = new float[N*N];
    }

    private int IX(int x, int y){
        x = PApplet.constrain(x, 0, N-1);
        y = PApplet.constrain(y, 0, N-1);
        return x + (y * N);
    }

    void addDensity(int x, int y, float amount){
        int index  = IX(x, y);
        this.density[index] += amount;
    }

    void addVelocity(int x , int y, float amountX, float amountY){
        // Gives a velocity to the introduced fluid
        int index= IX(x, y);
        this.vx[index] += amountX;
        this.vy[index] += amountY;
    }

    public void step() {
        float visc     = this.viscosity;
        float diff     = this.diff;
        float dt       = this.dt;
        float[] Vx      = this.vx;
        float[] Vy      = this.vy;
        float[] Vx0     = this.vx0;
        float[] Vy0     = this.vy0;
        float[] s       = this.s;
        float[] density = this.density;

        diffuse(1, Vx0, Vx, visc, dt);
        diffuse(2, Vy0, Vy, visc, dt);

        project(Vx0, Vy0, Vx, Vy);

        advect(1, Vx, Vx0, Vx0, Vy0, dt);
        advect(2, Vy, Vy0, Vx0, Vy0, dt);

        project(Vx, Vy, Vx0, Vy0);

        diffuse(0, s, density, diff, dt);
        advect(0, density, s, Vx, Vy, dt);
    }

    void set_bnd(int b, float[] x) {
        for (int i = 1; i < N - 1; i++) {
            x[IX(i, 0  )] = b == 2 ? -x[IX(i, 1  )] : x[IX(i, 1 )];
            x[IX(i, N-1)] = b == 2 ? -x[IX(i, N-2)] : x[IX(i, N-2)];
        }
        for (int j = 1; j < N - 1; j++) {
            x[IX(0, j)] = b == 1 ? -x[IX(1, j)] : x[IX(1, j)];
            x[IX(N-1, j)] = b == 1 ? -x[IX(N-2, j)] : x[IX(N-2, j)];
        }

        x[IX(0, 0)] = 0.5f * (x[IX(1, 0)] + x[IX(0, 1)]);
        x[IX(0, N-1)] = 0.5f * (x[IX(1, N-1)] + x[IX(0, N-2)]);
        x[IX(N-1, 0)] = 0.5f * (x[IX(N-2, 0)] + x[IX(N-1, 1)]);
        x[IX(N-1, N-1)] = 0.5f * (x[IX(N-2, N-1)] + x[IX(N-1, N-2)]);
    }

    void project(float[] velocX, float[] velocY, float[] p, float[] div) {
        for (int j = 1; j < this.size - 1; j++) {
            for (int i = 1; i < this.size - 1; i++) {
                div[IX(i, j)] = -0.5f*(
                        velocX[IX(i+1, j)]
                                -velocX[IX(i-1, j)]
                                +velocY[IX(i, j+1)]
                                -velocY[IX(i, j-1)]
                )/this.size;
                p[IX(i, j)] = 0;
            }
        }

        set_bnd(0, div);
        set_bnd(0, p);
        lin_solve(0, p, div, 1, 6);

        for (int j = 1; j < this.size - 1; j++) {
            for (int i = 1; i < this.size - 1; i++) {
                velocX[IX(i, j)] -= 0.5f * (  p[IX(i+1, j)]
                        -p[IX(i-1, j)]) * this.size;
                velocY[IX(i, j)] -= 0.5f * (  p[IX(i, j+1)]
                        -p[IX(i, j-1)]) * this.size;
            }
        }
        set_bnd(1, velocX);
        set_bnd(2, velocY);
    }
    void advect(int b, float[] d, float[] d0, float[] velocX, float[] velocY, float dt) {
        float i0, i1, j0, j1;

        float dtx = dt * (N - 2);
        float dty = dt * (N - 2);

        float s0, s1, t0, t1;
        float tmp1, tmp2, x, y;

        float Nfloat = N;
        float ifloat, jfloat;
        int i, j;

        for (j = 1, jfloat = 1; j < N - 1; j++, jfloat++) {
            for (i = 1, ifloat = 1; i < N - 1; i++, ifloat++) {
                tmp1 = dtx * velocX[IX(i, j)];
                tmp2 = dty * velocY[IX(i, j)];
                x    = ifloat - tmp1;
                y    = jfloat - tmp2;

                if (x < 0.5f) x = 0.5f;
                if (x > Nfloat + 0.5f) x = Nfloat + 0.5f;
                i0 = (float) Math.floor(x);
                i1 = i0 + 1.0f;
                if (y < 0.5f) y = 0.5f;
                if (y > Nfloat + 0.5f) y = Nfloat + 0.5f;
                j0 = (float) Math.floor(y);
                j1 = j0 + 1.0f;

                s1 = x - i0;
                s0 = 1.0f - s1;
                t1 = y - j0;
                t0 = 1.0f - t1;

                int i0i = (int)i0;
                int i1i = (int)i1;
                int j0i = (int)j0;
                int j1i = (int)j1;

                // DOUBLE CHECK THIS!!!
                d[IX(i, j)] =
                        s0 * (t0 * d0[IX(i0i, j0i)] + t1 * d0[IX(i0i, j1i)]) +
                                s1 * (t0 * d0[IX(i1i, j0i)] + t1 * d0[IX(i1i, j1i)]);
            }
        }

        set_bnd(b, d);
    }
    private void lin_solve(int b, float[] x, float[] x0, float a, float c) {
        float cRecip = 1.0F / c;
        for (int k = 0; k < iter; k++) {
            for (int j = 1; j < N - 1; j++) {
                for (int i = 1; i < N - 1; i++) {
                    x[IX(i, j)] =
                            (x0[IX(i, j)]
                                    + a*(    x[IX(i+1, j)]
                                    +x[IX(i-1, j)]
                                    +x[IX(i, j+1)]
                                    +x[IX(i, j-1)]
                            )) * cRecip;
                }
            }

            set_bnd(b, x);
        }
    }
    private void diffuse(int b, float [] x, float [] x0, float diff, float dt){
        float a = dt * diff * (N-2) *(N-2);
        lin_solve(b, x, x0, a, 1 + 6 * a);
        set_bnd(b, x);
    }

    void renderDensity(int v1, int v2, int v3){
        for(int i = 0; i<N; i++){
            for(int j= 0; j<N ;j++){
                float x = i * p5.SCALE;
                float y = j * p5.SCALE;
                float d = this.density[IX(i, j)];
                p5.fill(v1, v2, v3, d);
                p5.noStroke();
                p5.square(x, y, p5.SCALE);
            }
        }
    }
    void fadeD(){
        for(int i = 0; i< this.density.length ; i++){
            float d = density[i];
            density[i] = PApplet.constrain((float) (d-0.01), 0, 255);
        }
    }
}

package io.github.acien101.diedricoanimation.openGL;

/**
 * Created by amil101 on 3/08/16.
 */
public class Model {
    private int NumVerts;

    private float[] Verts;

    public Model(int Numverts, float[] Verts){
        this.NumVerts = Numverts;
        this.Verts = Verts;
    }

    public int getNumVerts() {
        return NumVerts;
    }

    public float[] getVerts() {
        return Verts;
    }
}

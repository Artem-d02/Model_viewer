package engine.maths;

import Mtrx.Matrix;

public class Matrix4f extends Matrix<Float> {
    public static final int SIZE = 4;
    public Matrix4f() {
        super(4, 4);
    }
    public Float[] toArray() {
        Float[] data = new Float[getSizeX() * getSizeY()];
        for (int yIndex = 0; yIndex < getSizeY(); yIndex++) {
            for (int xIndex = 0; xIndex < getSizeX(); xIndex++) {
                data[getSizeX() * yIndex + xIndex] = get(xIndex, yIndex);
            }
        }
        return data;
    }
}

package engine.maths;

import Mtrx.Matrix;
import Mtrx.SquareMatrix;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;


public class Matrix4f extends SquareMatrix<Float> {
    public static final int SIZE = 4;
    public Matrix4f() {
        super(SIZE, 0.0f);
    }
    public Matrix4f(final @NotNull List<List<Float>> inputData) throws IllegalArgumentException {
        super(inputData);
        if (inputData.size() != SIZE) {
            throw new IllegalArgumentException("Error: matrix size should be equal to 4");
        }
    }
    public Float[] toArray() {
        Float[] data = new Float[getSize() * getSize()];
        for (int yIndex = 0; yIndex < getSize(); yIndex++) {
            for (int xIndex = 0; xIndex < getSize(); xIndex++) {
                data[getSize() * yIndex + xIndex] = get(xIndex, yIndex);
            }
        }
        return data;
    }
    public static @NotNull Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            result.set(i, i, 1.0f);
        }
        return result;
    }
    public static @NotNull Matrix4f translate(final @NotNull Vector3f vector) {
        Matrix4f translationMat = identity();
        translationMat.set(3,0, vector.getX());
        translationMat.set(3,1, vector.getY());
        translationMat.set(3,2, vector.getZ());
        return translationMat;
    }
    public static @NotNull Matrix4f rotation(final float angle, final @NotNull Vector3f axis) {
        float cos = (float)Math.cos(Math.toRadians(angle));
        float sin = (float)Math.sin(Math.toRadians(angle));
        float antiCos = 1 - cos;
        UnaryOperator<Float> sqr = x -> x * x;
        float Ux = axis.getX();
        float Uy = axis.getY();
        float Uz = axis.getZ();
        Matrix4f rotationMat = new Matrix4f(
                Arrays.asList(
                        Arrays.asList(
                                cos + sqr.apply(Ux) * antiCos,  Ux * Uy * antiCos - Uz * sin,   Ux * Uz * antiCos + Uy * sin
                        ),
                        Arrays.asList(
                                Uy * Ux * antiCos + Uz * sin,   cos + sqr.apply(Uy) * antiCos,  Uy * Uz * antiCos - Ux * sin
                        ),
                        Arrays.asList(
                                Uz * Ux * antiCos - Uy * sin,   Uz * Uy * antiCos + Ux * sin,   cos + sqr.apply(Uz) * antiCos
                        )
                )
        );
        return rotationMat;
    }
    public static @NotNull Matrix4f scale(final @NotNull Vector3f scalar) {
        Matrix4f result = identity();
        for (int i = 0; i < scalar.length(); i++) {
            result.set(i, i, result.get(i, i) * scalar.get(i));
        }
        return result;
    }
    public static @NotNull Matrix4f multiply(final @NotNull Matrix4f first, final @NotNull Matrix4f second) {
        Matrix4f result = new Matrix4f();
        BinaryOperator<List<Float>> scalarMultiply = (firstVec, secondVec) -> {
            if (firstVec.size() != secondVec.size())
                throw new IllegalArgumentException("Error: couldn't multiply vectors with different sizes");
            ArrayList<Float> res = new ArrayList<>(firstVec);
            for (int i = 0; i < firstVec.size(); i++)
                res.set(i, res.get(i) * secondVec.get(i));
            return res;
        };
        Function<List<Float>, Float> matrixElem = vec -> {
            Float sum = 0.0f;
            for (Float elem : vec)
                sum += elem;
            return sum;
        };
        for (int firstIndex = 0; firstIndex < SIZE; firstIndex++) {
            for (int secondIndex = 0; secondIndex < SIZE; secondIndex++) {
                result.set(secondIndex, firstIndex, matrixElem.apply(scalarMultiply.apply(first.getRaw(firstIndex), second.getColumn(secondIndex))));
            }
        }
        return result;
    }
}

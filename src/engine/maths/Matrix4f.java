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
                                cos + sqr.apply(Ux) * antiCos,  Ux * Uy * antiCos - Uz * sin,   Ux * Uz * antiCos + Uy * sin,  0.0f
                        ),
                        Arrays.asList(
                                Uy * Ux * antiCos + Uz * sin,   cos + sqr.apply(Uy) * antiCos,  Uy * Uz * antiCos - Ux * sin,  0.0f
                        ),
                        Arrays.asList(
                                Uz * Ux * antiCos - Uy * sin,   Uz * Uy * antiCos + Ux * sin,   cos + sqr.apply(Uz) * antiCos, 0.0f
                        ),
                        Arrays.asList(
                                0.0f,                           0.0f,                           0.0f,                          1.0f
                        )
                )
        );
        return rotationMat;
    }
    public static @NotNull Matrix4f projection(final float fov, final float aspect, final float near, final float far) {
        Matrix4f projectionMat = new Matrix4f(
                Arrays.asList(
                        Arrays.asList(
                                (float)(1 / (aspect * Math.tan(fov / 2))),  0.0f,                           0.0f,                         0.0f
                        ),
                        Arrays.asList(
                                0.0f,                                       (float)(1 / Math.tan(fov / 2)), 0.0f,                         0.0f
                        ),
                        Arrays.asList(
                                0.0f,                                       0.0f,                           -(far + near)/(far - near),   -(2 * far * near)/(far - near)
                        ),
                        Arrays.asList(
                                0.0f,                                       0.0f,                           -1.0f,                        0.0f
                        )
                )
        );
        return projectionMat;
    }
    public static @NotNull Matrix4f view(final @NotNull Vector3f position, final @NotNull Vector3f rotation) {
        Matrix4f translationMatrix = Matrix4f.translate(new Vector3f(-position.getX(), -position.getY(), -position.getZ()));
        Matrix4f rotX = Matrix4f.rotation(rotation.getX(), new Vector3f(1, 0, 0));
        Matrix4f rotY = Matrix4f.rotation(rotation.getY(), new Vector3f(0, 1, 0));
        Matrix4f rotZ = Matrix4f.rotation(rotation.getZ(), new Vector3f(0, 0, 1));

        Matrix4f rotationMatrix = Matrix4f.multiply(rotZ, Matrix4f.multiply(rotY, rotX));

        return Matrix4f.multiply(translationMatrix, rotationMatrix);
    }
    public static @NotNull Matrix4f lookAt(final @NotNull Vector3f right, final @NotNull Vector3f up, final @NotNull Vector3f direction, final @NotNull Vector3f position) {
        Matrix4f translationMatrix = Matrix4f.translate(new Vector3f(-position.getX(), -position.getY(), -position.getZ()));
        Matrix4f first = new Matrix4f(
                Arrays.asList(
                        Arrays.asList(
                                right.getX(),       right.getY(),       right.getZ(),       0.0f
                        ),
                        Arrays.asList(
                                up.getX(),          up.getY(),          up.getZ(),          0.0f
                        ),
                        Arrays.asList(
                                direction.getX(),   direction.getY(),   direction.getZ(),   0.0f
                        ),
                        Arrays.asList(
                                0.0f,               0.0f,               0.0f,               1.0f
                        )
                )
        );
        return Matrix4f.multiply(first, translationMatrix);
    }
    public static @NotNull Matrix4f viewThroughLookAt(final @NotNull Vector3f position, final @NotNull Vector3f rotation) {
        Matrix4f rotationMat = Matrix4f.multiply(Matrix4f.rotation(rotation.getY(), new Vector3f(0, 1, 0)), Matrix4f.rotation(rotation.getX(), new Vector3f(1, 0, 0)));
        rotationMat = Matrix4f.multiply(Matrix4f.rotation(rotation.getZ(), new Vector3f(0, 0, 1)), rotationMat);
        Vector3f right = Vector3f.multiply(rotationMat, new Vector3f(-1, 0, 0));
        Vector3f up = Vector3f.multiply(rotationMat, new Vector3f(0, 1, 0));
        Vector3f direction = Vector3f.multiply(rotationMat, new Vector3f(0, 0, -1));
        return lookAt(right, up, direction, position);
    }
    public static @NotNull Matrix4f scale(final @NotNull Vector3f scalar) {
        Matrix4f result = identity();
        for (int i = 0; i < scalar.size(); i++) {
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
    public static @NotNull Matrix4f transform(final @NotNull Vector3f position, final @NotNull Vector3f rotation, final @NotNull Vector3f scale) {
        Matrix4f translationMatrix = Matrix4f.translate(position);
        Matrix4f rotX = Matrix4f.rotation(rotation.getX(), new Vector3f(1, 0, 0));
        Matrix4f rotY = Matrix4f.rotation(rotation.getY(), new Vector3f(0, 1, 0));
        Matrix4f rotZ = Matrix4f.rotation(rotation.getZ(), new Vector3f(0, 0, 1));
        Matrix4f scaleMatrix = Matrix4f.scale(scale);

        Matrix4f rotationMatrix = Matrix4f.multiply(rotX, Matrix4f.multiply(rotY, rotZ));

        return Matrix4f.multiply(translationMatrix, Matrix4f.multiply(rotationMatrix, scaleMatrix));
    }
}

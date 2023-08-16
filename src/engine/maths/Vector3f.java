package engine.maths;

import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Vector3f {
    private float x = 0;
    private float y = 0;
    private float z = 0;
    public Vector3f() {}
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void set(final @NotNull Vector3f another) {
        x = another.x;
        y = another.y;
        z = another.z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public static int size() {
        return 3;
    }
    public Float get(final int index) throws IndexOutOfBoundsException {
        switch (index) {
            case 0:
                return getX();
            case 1:
                return getY();
            case 2:
                return getZ();
            default:
                throw new IndexOutOfBoundsException("Fatal error: out of bounds in Vector2f");
        }
    }
    public static @NotNull Vector3f add(final @NotNull Vector3f first, final @NotNull Vector3f second) {
        return new Vector3f(first.getX() + second.getX(), first.getY() + second.getY(), first.getZ() + second.getZ());
    }
    public static @NotNull Vector3f sub(final @NotNull Vector3f first, final @NotNull Vector3f second) {
        return new Vector3f(first.getX() - second.getX(), first.getY() - second.getY(), first.getZ() - second.getZ());
    }
    public static @NotNull Vector3f coordsMultiply(final @NotNull Vector3f first, final @NotNull Vector3f second) {
        return new Vector3f(first.getX() * second.getX(), first.getY() * second.getY(), first.getZ() * second.getZ());
    }
    public static @NotNull Vector3f divideCoords(final @NotNull Vector3f first, final @NotNull Vector3f second) {
        return new Vector3f(first.getX() / second.getX(), first.getY() / second.getY(), first.getZ() / second.getZ());
    }
    public float length() {
        UnaryOperator<Float> sqr = x -> x * x;
        return (float) Math.sqrt(sqr.apply(x) + sqr.apply(y) + sqr.apply(z));
    }
    public static @NotNull Vector3f normalize(final @NotNull Vector3f vector) {
        float norm = vector.length();
        return new Vector3f(vector.x / norm, vector.y / norm, vector.z / norm);
    }
    public static float dot(final @NotNull Vector3f first, final @NotNull Vector3f second) {
        return first.getX() * second.getX() + first.getY() * second.getY() + first.getZ() * second.getZ();
    }
    public void add(final @NotNull Vector3f vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
    }
    public static @NotNull Vector3f multiply(final @NotNull Matrix4f mat, final @NotNull Vector3f vec) {
        BinaryOperator<List<Float>> scalarMultiply = (firstVec, secondVec) -> {
            if (firstVec.size() != secondVec.size())
                throw new IllegalArgumentException("Error: couldn't multiply vectors with different sizes");
            ArrayList<Float> res = new ArrayList<>(firstVec);
            for (int i = 0; i < firstVec.size(); i++)
                res.set(i, res.get(i) * secondVec.get(i));
            return res;
        };
        Function<List<Float>, Float> resultElem = v -> {
            Float sum = 0.0f;
            for (Float elem : v)
                sum += elem;
            return sum;
        };
        ArrayList<Float> vec4 = new ArrayList<>(mat.getSize());
        vec4.add(vec.x);
        vec4.add(vec.y);
        vec4.add(vec.z);
        vec4.add(1.0f);
        ArrayList<Float> result = new ArrayList<>(mat.getSize());
        for (int i = 0; i < mat.getSize(); i++) {
            result.add(resultElem.apply(scalarMultiply.apply(mat.getRaw(i), vec4)));
        }
        return new Vector3f(result.get(0), result.get(1), result.get(2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        return Float.compare(vector3f.x, x) == 0 && Float.compare(vector3f.y, y) == 0 && Float.compare(vector3f.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
    public void print(PrintStream out) {
        out.println(x + " " + y + " " + z);
    }
}

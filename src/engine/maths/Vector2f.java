package engine.maths;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.UnaryOperator;

public class Vector2f {
    private float x;
    private float y;
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
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
    public static int size() {
        return 2;
    }
    public Float get(final int index) throws IndexOutOfBoundsException {
        switch (index) {
            case 0:
                return getX();
            case 1:
                return getY();
            default:
                throw new IndexOutOfBoundsException("Fatal error: out of bounds in Vector2f");
        }
    }
    public static @NotNull Vector2f add(final @NotNull Vector2f first, final @NotNull Vector2f second) {
        return new Vector2f(first.getX() + second.getX(), first.getY() + second.getY());
    }
    public static @NotNull Vector2f sub(final @NotNull Vector2f first, final @NotNull Vector2f second) {
        return new Vector2f(first.getX() - second.getX(), first.getY() - second.getY());
    }
    public static @NotNull Vector2f coordsMultiply(final @NotNull Vector2f first, final @NotNull Vector2f second) {
        return new Vector2f(first.getX() * second.getX(), first.getY() * second.getY());
    }
    public static @NotNull Vector2f divideCoords(final @NotNull Vector2f first, final @NotNull Vector2f second) {
        return new Vector2f(first.getX() / second.getX(), first.getY() / second.getY());
    }
    public float length() {
        UnaryOperator<Float> sqr = x -> x * x;
        return (float) Math.sqrt(sqr.apply(x) + sqr.apply(y));
    }
    public static @NotNull Vector2f normalize(final @NotNull Vector2f vector) {
        float norm = vector.length();
        return new Vector2f(vector.x / norm, vector.y / norm);
    }
    public static float dot(final @NotNull Vector2f first, final @NotNull Vector2f second) {
        return first.getX() * second.getX() + first.getY() * second.getY();
    }
    public void add(final @NotNull Vector2f vec) {
        this.x += vec.getX();
        this.y += vec.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        return Float.compare(vector2f.x, x) == 0 && Float.compare(vector2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

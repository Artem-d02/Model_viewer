package engine.maths;

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
    public int length() {
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
}

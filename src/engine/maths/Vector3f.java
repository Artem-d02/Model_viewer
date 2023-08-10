package engine.maths;

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
    public int length() {
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
}

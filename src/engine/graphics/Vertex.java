package engine.graphics;

import engine.maths.Vector3f;

public class Vertex {
    private Vector3f position;
    private Vector3f color = new Vector3f(1.0f, 0.0f, 0.0f);

    public Vertex(Vector3f position) {
        this.position = position;
    }
    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }
}

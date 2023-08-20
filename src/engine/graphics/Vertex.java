package engine.graphics;

import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class Vertex {
    private Vector3f position;
    private Vector3f color = new Vector3f(1.0f, 0.0f, 0.0f);
    private Vector3f normal;
    private Vector2f textureCoords;

    public Vertex(Vector3f position) {
        this.position = position;
    }
    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vertex(Vector3f position, Vector3f normal, Vector2f textureCoords) {
        this.position = position;
        this.normal = normal;
        this.textureCoords = textureCoords;
    }

    public Vertex(Vector3f position, Vector2f textureCoords) {
        this.position = position;
        this.textureCoords = textureCoords;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector2f getTextureCoords() {
        return textureCoords;
    }

    public Vector3f getNormal() {
        return normal;
    }
}

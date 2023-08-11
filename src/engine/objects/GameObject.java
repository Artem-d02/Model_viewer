package engine.objects;

import Main.Main;
import engine.graphics.Mesh;
import engine.maths.Vector3f;

public class GameObject {
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private Mesh mesh;
    private static float temp = 0;

    public GameObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void update() {
        temp += 0.02;
        position.setX((float) Math.sin(temp));
        rotation.set((float) Math.sin(temp) * 360, (float) Math.sin(temp) * 360, (float) Math.sin(temp) * 360);
        scale.set((float) Math.sin(temp), (float) Math.sin(temp), (float) Math.sin(temp));
    }
}

package engine.objects;

import engine.io.Input;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;
    private float moveSpeed = 0.05f;
    private Input input;

    public Camera(Vector3f position, Vector3f rotation, Input input) {
        this.position = position;
        this.rotation = rotation;
        this.input = input;
    }
    public void update() {
        if (input.isKeyDown(GLFW.GLFW_KEY_A))
            position.add(new Vector3f(-moveSpeed, 0, 0));
        if (input.isKeyDown(GLFW.GLFW_KEY_D))
            position.add(new Vector3f(moveSpeed, 0, 0));
        if (input.isKeyDown(GLFW.GLFW_KEY_W))
            position.add(new Vector3f(0, 0, -moveSpeed));
        if (input.isKeyDown(GLFW.GLFW_KEY_S))
            position.add(new Vector3f(0, 0, moveSpeed));
        if (input.isKeyDown(GLFW.GLFW_KEY_SPACE))
            position.add(new Vector3f(0, moveSpeed, 0));
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
            position.add(new Vector3f(0, -moveSpeed, 0));
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}

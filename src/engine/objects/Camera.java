package engine.objects;

import Main.Main;
import engine.io.Input;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;
    private float moveSpeed = 0.05f;
    private float mouseSensitivity = 0.5f;
    private Input input;
    private double oldMouseX;
    private double oldMouseY;
    private double newMouseX;
    private double newMouseY;

    public Camera(Vector3f position, Vector3f rotation, Input input) {
        this.position = position;
        this.rotation = rotation;
        this.input = input;
        this.oldMouseX = input.getMouseX();
        this.oldMouseY = input.getMouseY();
    }
    public void update() {
        newMouseX = input.getMouseX();
        newMouseY = input.getMouseY();

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float y = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

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

        float dx = (float)(newMouseX - oldMouseX);
        float dy = (float)(newMouseY - oldMouseY);

        rotation.add(new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        System.out.println(position.getX() + " " + position.getY() + " " + position.getZ());
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}

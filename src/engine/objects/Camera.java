package engine.objects;

import Main.Main;
import engine.io.Input;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;
    private float moveSpeed = 0.05f;
    private float mouseSensitivity = 0.15f;
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
        float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;

        if (input.isKeyDown(GLFW.GLFW_KEY_A))
            position.add(new Vector3f(z, 0, -x));
        if (input.isKeyDown(GLFW.GLFW_KEY_D))
            position.add(new Vector3f(-z, 0, x));
        if (input.isKeyDown(GLFW.GLFW_KEY_W))
            position.add(new Vector3f(x, 0, z));
        if (input.isKeyDown(GLFW.GLFW_KEY_S))
            position.add(new Vector3f(-x, 0, -z));
        if (input.isKeyDown(GLFW.GLFW_KEY_SPACE))
            position.add(new Vector3f(0, moveSpeed, 0));
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
            position.add(new Vector3f(0, -moveSpeed, 0));

        float dx = (float)(newMouseX - oldMouseX);
        float dy = (float)(newMouseY - oldMouseY);

        Vector3f newRotation = Vector3f.add(rotation, new Vector3f(dy * mouseSensitivity, -dx * mouseSensitivity, 0));
        if (newRotation.getX() < -90) {
            newRotation.setX(-90);
        }
        if (newRotation.getX() > 90) {
            newRotation.setX(90);
        }
        rotation.set(newRotation);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;

        //System.out.println(position.getX() + " " + position.getY() + " " + position.getZ());
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}

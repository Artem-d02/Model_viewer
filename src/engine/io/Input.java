package engine.io;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LAST;

public class Input {
    private boolean[] keys = new boolean[GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private double mouseX;
    private double mouseY;
    private GLFWKeyCallback keyboard;
    private GLFWMouseButtonCallback mousButtons;
    private GLFWCursorPosCallback mouseMove;
    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {

            }
        };
    }
}

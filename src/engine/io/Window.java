package engine.io;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private int width;
    private int height;
    private String title;
    private long window;
    private int frames;
    private long time;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() throws IllegalStateException {
        if (!glfwInit()) {
            throw new IllegalStateException("Fatal error: GLFW wasn't initialized");
        }

        window = glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            throw new IllegalStateException("Fatal error: window wasn't created");
        }

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    public void update() {
        glfwPollEvents();
        frames++;
        if (System.currentTimeMillis() - time > 1000) {
            time = System.currentTimeMillis();
            glfwSetWindowTitle(window, title + " | FPS: " + frames);
            frames = 0;
        }
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    protected void finalize() throws Throwable {
        glfwDestroyWindow(window);
    }
}

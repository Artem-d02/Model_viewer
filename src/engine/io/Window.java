package engine.io;

import engine.maths.Vector3f;
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
    private Input input;
    private Vector3f background = new Vector3f();
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private  boolean isFullScreen;
    private int[] windowPosX = new int[1];
    private int[] windowPosY = new int[1];

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() throws IllegalStateException {
        if (!glfwInit()) {
            throw new IllegalStateException("Fatal error: GLFW wasn't initialized");
        }

        window = glfwCreateWindow(width, height, title, isFullScreen ? glfwGetPrimaryMonitor() : 0, 0);

        input = new Input();

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

            windowPosX[0] = (vidmode.width() - pWidth.get(0)) / 2;
            windowPosY[0] = (vidmode.height() - pHeight.get(0)) / 2;

            // Center the window
            glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        // For correct working of GL
        GL.createCapabilities();

        GL11.glEnable(GL_DEPTH_TEST);

        // Set callbacks
        createCallbacks();

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        glfwSetKeyCallback(window, input.getKeyboardCallback());
        glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        glfwSetWindowSizeCallback(window, sizeCallback);
        glfwSetScrollCallback(window, input.getMouseScrollCallback());
    }

    public void update() {
        if (isResized) {
            GL11.glViewport(0, 0, width, height);
            isResized = false;

        }
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
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

    public void destroy() throws Throwable {
        input.destroy();
        sizeCallback.free();
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public Input getInput() {
        return input;
    }

    public void setBackgroundColor(float r, float g, float b) {
        background.set(r, g, b);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        isResized = true;
        if (isFullScreen) {
            glfwGetWindowPos(window, windowPosX, windowPosY);
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }
}

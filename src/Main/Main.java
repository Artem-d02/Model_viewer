package Main;

import engine.graphics.*;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main implements Runnable {
    public Thread game;
    public static int WIDTH = 1280;
    public static int HEIGHT = 780;
    private Window window;
    private Renderer renderer;
    private Shader shader;

    public Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.0f),  new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f),new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f))
    }, new int[]{
            0, 1, 2,
            0, 3, 2
    }, new Material("/textures/texture3.png")
    );

    public void start() {
        game = new Thread(this,"game");
        game.start();
    }

    public void init() {
        System.out.println("Initializing game!");
        shader = new Shader("./resources/shaders/mainVertex.glsl", "./resources/shaders/mainFragment.glsl");
        window = new Window(WIDTH, HEIGHT, "My window");
        renderer = new Renderer(shader);
        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        mesh.create();
        shader.create();
    }
    public void run() {
        try {
            init();
            while(!window.shouldClose() && !window.getInput().isKeyDown(GLFW_KEY_ESCAPE)) {
                update();
                render();
                if (window.getInput().isKeyDown(GLFW_KEY_F11))
                    window.setFullScreen(!window.isFullScreen());
            }
            destroy();
        } catch (Throwable e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
    private void update() {
        window.update();
        if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT))
            System.out.println("This mouse position:\n" + "X: " + window.getInput().getMouseX() + ", Y: " + window.getInput().getMouseY());
    }

    private void render() {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    private void destroy() throws Throwable {
        window.destroy();
        shader.destroy();
        mesh.destroy();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
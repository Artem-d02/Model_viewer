package Main;

import engine.graphics.*;
import engine.io.Input;
import engine.io.ModelLoader;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.GameObject;
import test.Tester;

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

    public Mesh mesh = ModelLoader.loadModel("./resources/models/dragon.obj", "/textures/texture3.png");
    private GameObject gameObject = new GameObject(mesh, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
    private Camera camera;
    public void start() {
        game = new Thread(this,"game");
        game.start();
    }

    public void init() {
        System.out.println("Initializing game!");
        shader = new Shader("./resources/shaders/mainVertex.glsl", "./resources/shaders/mainFragment.glsl");
        window = new Window(WIDTH, HEIGHT, "My window");
        renderer = new Renderer(window, shader);
        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        mesh.create();
        shader.create();
        camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0), window.getInput());
    }
    public void run() {
        try {
            init();
            while(!window.shouldClose() && !window.getInput().isKeyDown(GLFW_KEY_ESCAPE)) {
                update();
                render();
                if (window.getInput().isKeyDown(GLFW_KEY_F11))
                    window.setFullScreen(!window.isFullScreen());
                if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                    window.mouseLock(true);
                }

            }
            destroy();
        } catch (Throwable e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
    }
    private void update() {
        window.update();
        camera.update(gameObject);
        gameObject.update();
        //if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT))
        //    System.out.println("This mouse position:\n" + "X: " + window.getInput().getMouseX() + ", Y: " + window.getInput().getMouseY());
    }

    private void render() {
        renderer.renderMesh(gameObject, camera);
        window.swapBuffers();
    }

    private void destroy() throws Throwable {
        window.destroy();
        shader.destroy();
        mesh.destroy();
    }
    private static void test() {
        Tester.test();
    }

    public static void main(String[] args) {
        Main.test();
        new Main().start();
    }
}
package Main;

import engine.io.Window;

import static org.lwjgl.glfw.GLFW.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main implements Runnable {
    public Thread game;
    public static int WIDTH = 1280;
    public static int HEIGHT = 780;
    private Window window;

    public void start() {
        game = new Thread(this,"game");
        game.start();
    }

    public void init() {
        System.out.println("Initializing game!");
        window = new Window(WIDTH, HEIGHT, "My window");
        window.create();
    }
    public void run() {
        try {
            init();
            while(!window.shouldClose()) {
                update();
                render();
                if (window.getInput().isKeyDown(GLFW_KEY_ESCAPE))
                    break;
            }
            destroy();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    private void update() {
        window.update();
        if (window.getInput().isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT))
            System.out.println("This mouse position:\n" + "X: " + window.getInput().getMouseX() + ", Y: " + window.getInput().getMouseY());
    }

    private void render() {
        //System.out.println("Rendering game!");
        window.swapBuffers();
    }

    private void destroy() throws Throwable {
        window.destroy();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
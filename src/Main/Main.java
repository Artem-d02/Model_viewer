package Main;

import engine.io.Window;

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
        init();
        while(!window.shouldClose()) {
            update();
            render();
        }
    }
    private void update() {
        //System.out.println("Update game!");
        window.update();
    }

    private void render() {
        //System.out.println("Rendering game!");
        window.swapBuffers();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
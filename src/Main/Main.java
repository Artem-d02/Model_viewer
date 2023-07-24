package Main;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main implements Runnable {
    public Thread game;

    public void start() {
        game = new Thread(this,"game");
        game.start();
    }

    public static void init() {
        System.out.println("Initializing game!");
    }
    public void run() {
        init();
        while(true) {
            update();
            render();
        }
    }
    private void update() {
        System.out.println("Update game!");
    }

    private void render() {
        System.out.println("Rendering game!");
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
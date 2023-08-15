package test;

import engine.maths.Matrix4f;
import org.lwjglx.Sys;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Tester {
    private interface Testable {
        boolean test();
    }
    public static boolean matrixTest() {
        printTitle("Matrix tests");
        Matrix4f first = new Matrix4f(
                Arrays.asList(
                        Arrays.asList(1f, 2f, 3f, 4f),
                        Arrays.asList(5f, 6f, 7f, 8f),
                        Arrays.asList(9f, 10f, 11f, 12f),
                        Arrays.asList(13f, 14f, 15f, 16f)
                )
        );
        Matrix4f second = new Matrix4f(
                Arrays.asList(
                        Arrays.asList(1f, 2f, 3f, 4f),
                        Arrays.asList(2f, 3f, 4f, 5f),
                        Arrays.asList(3f, 4f, 5f, 6f),
                        Arrays.asList(4f, 5f, 6f, 7f)
                )
        );
        Matrix4f.multiply(first, second).print(System.out);
        return true;
    }
    public static void test() {
        ArrayList<Testable> tests = new ArrayList<>();

        //  add all tests
        tests.add(Tester::matrixTest);

        for (Testable testElem : tests) {
            boolean testResult = testElem.test();
            System.out.print("Test result:" + testResult);
        }
        System.out.println();
    }
    private static void printTitle(String message) {
        System.out.println();
        Runnable printStars = () -> {
            for (int i = 0; i < 50; i++) {
                System.out.print("*");
            }
        };
        printStars.run();
        System.out.println("\n" + message);
        printStars.run();
        System.out.print("\n");
    }
}

package engine.utils;

import java.io.*;

public class FileUtils {
    private static int recursionDeep = 0;
    public static String loadAsString(String path) throws IllegalStateException {
        StringBuilder result = new StringBuilder();
        //displayListFilesForFolder(new File(path));
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Couldn't find the file at " + path);
            throw new IllegalStateException("Couldn't find the file at " + path);
        }
        return result.toString();
    }
    //  only for debug
    public static void displayListFilesForFolder(final File folder) {
        Runnable shift = () -> {
            for (int i = 0; i < recursionDeep; i++) {
                System.out.print("\t");
            }
        };
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                for (int i = 0; i < recursionDeep; i++) {
                    System.out.print("\t");
                }
                System.out.println(fileEntry.getName());
                recursionDeep++;
                displayListFilesForFolder(fileEntry);
            } else {
                shift.run();
                System.out.println(fileEntry.getName());
            }
        }
        recursionDeep--;
    }
}

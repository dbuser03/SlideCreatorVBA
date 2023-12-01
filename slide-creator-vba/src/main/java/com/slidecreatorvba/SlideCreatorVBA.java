package com.slidecreatorvba;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * The SlideCreatorVBA class, the main class of the program.
 * 
 * Uses the default constructor.
*/

public class SlideCreatorVBA {

    /**
     * Clears the console.
     */

    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); // Check the current operating system
    
            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the file path from the user.
     * 
     * @param scanner The scanner object.
     * @return The file path.
     */

    public static String getFilePathFromUser(Scanner scanner) {
        String filePath = null;
        boolean isValid = false;

        System.out.println("\n\n");
        while (!isValid) {
            System.out.println("Please enter the file path:");
            filePath = scanner.nextLine();

            filePath = filePath.replaceAll("^\"|\"$|^'|'$", "");

            if (filePath != null && !filePath.isEmpty() && filePath.endsWith(".txt") && !Files.isDirectory(Paths.get(filePath))) {
                isValid = true;
            } else {
                System.out.println("\nThe file path is not valid. Please try again.");
            }
        }

        return filePath;
    }

    /**
     * Gets the number of slides from the user.
     * 
     * @param scanner The scanner object.
     * @param maxSlides The maximum number of slides.
     * @return The number of slides.
     */

    public static int getNumSlidesFromUser(Scanner scanner, int maxSlides) {
        int numSlides = 0;
        boolean isValid = false;

        System.out.println("");
        while (!isValid) {
            System.out.println("Please enter the number of slides:");
            if (scanner.hasNextInt()) {
                numSlides = scanner.nextInt();
                if (numSlides > 0 && numSlides <= maxSlides) {
                    isValid = true;
                } else {
                    System.out.println("\nThe number of slides is not valid. Please enter a number greater than 0 and less than " + (maxSlides + 1));
                }
            } else {
                System.out.println("\nThat's not a valid number. Please try again.");
                scanner.next(); // discard the invalid input
            }
        }

        scanner.nextLine(); // discard the rest of the line
        return numSlides;
    }

    /**
     * The main method.
     * 
     * @param args The command line arguments.
     * @throws IOException The IOException.
     * @throws InterruptedException The InterruptedException.
     */

    public static void main(String[] args) throws IOException, InterruptedException {

        clearConsole();

        System.out.println(" ____  _ _     _       ____                _           __     ______    _    ");
        System.out.println("/ ___|| (_) __| | ___ / ___|_ __ ___  __ _| |_ ___  _ _\\ \\   / / __ )  / \\   ");
        System.out.println("\\___ \\| | |/ _` |/ _ \\ |   | '__/ _ \\/ _` | __/ _ \\| '__\\ \\ / /|  _ \\ / _ \\  ");
        System.out.println(" ___) | | | (_| |  __/ |___| | |  __/ (_| | || (_) | |   \\ V / | |_) / ___ \\ ");
        System.out.println("|____/|_|_|\\__,_|\\___|\\____|_|  \\___|\\__,_|\\__\\___/|_|    \\_/  |____/_/   \\_\\");

        try (Scanner scanner = new Scanner(System.in)) {
            String filePath = getFilePathFromUser(scanner);
            List<String> sentences = FileInputHandler.readFileAsSentences(filePath);
            int maxSlides = sentences.size() / 3;
            int numSlides = getNumSlidesFromUser(scanner, maxSlides);

            PowerPoint powerPoint = new PowerPoint(sentences, numSlides);

            // Wait for the user to enable macros in PowerPoint
            System.out.println("\n---------------------------------------------");
            System.out.println("To run the macro in PowerPoint for the first time, you need to change some settings. Please follow these steps:");
            System.out.println();
            System.out.println("PowerPoint\n" +
                            "└── File\n" +
                            "    └── Options\n" +
                            "        └── Trust Center\n" +
                            "            └── Trust Center Settings\n" +
                            "                └── Macro Settings\n" +
                            "                    ├── Enable all macros\n" +
                            "                    └── Consider reliable the access to the object model of VBA projects");
            
            System.out.println();
            System.out.println("Press enter to continue or (ctrl+c) to close the program...");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            
                // Run the macro only if the user has enabled macros in PowerPoint and has pressed enter
                VBAAutomation vbaAutomation = new VBAAutomation();
                vbaAutomation.completeVBA(powerPoint);
            
                PowerShellExecutor executor = new PowerShellExecutor("slide-creator-vba\\src\\main\\java\\com\\slidecreatorvba\\VBARunMacro.ps1");
                executor.execute();
            } else {
                System.out.println("\nNo input detected. The program will now exit.");
                System.exit(0);
            }
        }
    }
}

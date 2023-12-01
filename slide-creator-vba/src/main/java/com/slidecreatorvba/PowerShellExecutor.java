package com.slidecreatorvba;

import java.util.Scanner;
import java.io.IOException;

/**
 * A class that run the PowerShell script.
*/ 

public class PowerShellExecutor {
    private String scriptPath;

    /**
     * Default constructor for the PowerShellExecutor class.
     * 
     * @param scriptPath The path to the PowerShell script.
    */

    public PowerShellExecutor(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    /**
     * Executes the PowerShell script.
     * 
     * @throws IOException If an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read.
     * @throws InterruptedException If the current thread is interrupted by another thread while it is waiting, then the wait is ended and an InterruptedException is thrown.
    */

    public void execute() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-ExecutionPolicy", "Bypass", "-File", scriptPath);
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();
        try (Scanner powerShellScanner = new Scanner(process.getInputStream())) {
            while (powerShellScanner.hasNextLine()) {
                output.append(powerShellScanner.nextLine());
            }
        }

        StringBuilder errors = new StringBuilder();
        try (Scanner powerShellScanner = new Scanner(process.getErrorStream())) {
            while (powerShellScanner.hasNextLine()) {
                errors.append(powerShellScanner.nextLine());
            }
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("PowerShell script executed successfully!");
        } else {
            System.out.println("PowerShell script executed with errors. Please check the errors below:");
            System.out.println(errors);
        }
    }
}

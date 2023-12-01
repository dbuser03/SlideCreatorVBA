package com.slidecreatorvba;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A class that handles file input.
*/

public class FileInputHandler {

    /**
     * Default constructor for the FileInputHandler class.
    */

    public FileInputHandler() {
        // constructor body
    }

    /**
     * Reads a file and returns its contents as a list of sentences.
     * A sentence is defined as a string of text terminated by a ".", "!" or "?".
     * 
     * @param filePath The path to the file to read. Must be a .txt file.
     * @return A list of sentences from the file.
     * @throws IOException If an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read.
     * @throws IllegalArgumentException If the filePath is null, empty, a directory, or does not end with ".txt".
    */
    public static List<String> readFileAsSentences(String filePath) throws IOException {
        List<String> sentences = new ArrayList<>();

        if (filePath.endsWith(".txt") && filePath != null && !filePath.isEmpty() && !filePath.endsWith("\\")) {
            try (Scanner fileScanner = new Scanner(Paths.get(filePath))) {
                fileScanner.useDelimiter("[.!?]");
                while (fileScanner.hasNext()) {
                    sentences.add(fileScanner.next().trim());
                }
            } catch (IOException e) {
                throw new IOException("The file path is not valid.", e);
            }
        } else {
            throw new IllegalArgumentException("The file path is not valid.");
        }

        return sentences;
    }
}


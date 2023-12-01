package com.slidecreatorvba;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TextRank class is a class that takes the output of the FileInputHandler class (a list of sentences)
 * and ranks them based on a TextRank algorithm.
 * 
 * Future improvements:
 * 1. Stemming or Lemmatization: Words could be reduced to their root form. For example, "running", "runs", and "ran" would all be reduced to "run". 
 *    This would help to combine different forms of the same word, which would likely improve the quality of the keyword extraction.
 * 2. TF-IDF: Instead of just counting the frequency of each word (term frequency), you could also take into account how common the word is across all documents (inverse document frequency).
 *    This would give higher weight to words that are more unique to the document, which would likely improve the quality of the keyword extraction.
*/

public class TextRank {

    // Create a constant for the number of iterations and the damping factor
    private static final int ITERATIONS = 30;
    // The damping factor is a constant that determines how much the score of a sentence is influenced by the scores of the sentences it's connected to in the similarity matrix.
    private static final double DAMPING_FACTOR = 0.85;
    private List<String> sentences;

    /**
     * Constructor for TextRank class.
     *
     * @param sentences The list of sentences.
    */

    public TextRank(List<String> sentences) {
        this.sentences = sentences;
    }

    /**
     * Preprocesses a sentence by converting it to lowercase and removing punctuation.
     *
     * @param sentence The sentence to preprocess.
     * @return The preprocessed sentence.
    */

    private String preprocess(String sentence) {
        // Convert to lowercase
        String cleaned = sentence.toLowerCase();
        // Remove punctuation and text within parentheses
        cleaned = cleaned.replaceAll("\\(.*?\\)|[^a-zA-Z0-9\\s]", "");
        return cleaned;
    }

    /**
     * Generates n-grams from a sentence.
     *
     * @param sentence The sentence to generate n-grams from.
     * @param n The number of words per n-gram.
     * @return A list of n-grams.
    */
    
    private List<String> generateNGrams(String sentence, int n) {
        // Split the sentence into words
        List<String> ngrams = new ArrayList<>();
        String[] words = sentence.split("\\s+");

        // Loop through the words and add them to the list
        for (int i = 0; i < words.length - n + 1; i++) {
            // Create a string builder to store the n-gram
            StringBuilder ngram = new StringBuilder();
            // Loop through the words and add them to the string builder
            for (int j = i; j < i + n; j++) {
                // Add the word and a space to the string builder
                ngram.append(words[j]).append(' ');
            }
            // Add the n-gram to the list
            ngrams.add(ngram.toString().trim());
        }

        return ngrams;
    }

    /**
     * Returns the top sentences based on the summary length.
     *
     * @param summaryLength The length of the summary.
     * @return A list of sentences.
    */

    public Map<String, Double> rankSentences(int summaryLength) {
        // Insert the number of words per n-gram
        int n = 2;

        // Vectorize the sentences
        List<Map<String, Integer>> sentenceVectors = new ArrayList<>();

        // Create a method to vectorize the sentences
        for (String sentence : sentences) {
            // Create a map to store the word frequencies
            Map<String, Integer> vector = new HashMap<>();
            // Create a list of 2-grams from the words
            List<String> ngrams = generateNGrams(preprocess(sentence), n);
            for (String ngram : ngrams) {
                // Add the n-gram to the map
                vector.put(ngram, vector.getOrDefault(ngram, 0) + 1);
            }
            sentenceVectors.add(vector);
        }

        // Create a similarity matrix based on the vectors.
        double[][] similarityMatrix = new double[sentences.size()][sentences.size()];

        for (int i = 0; i < sentenceVectors.size(); i++) {
            for (int j = 0; j < sentenceVectors.size(); j++) {
                // Calculate the cosine similarity between the vectors
                if (i == j) {
                    // The similarity of a sentence with itself is 1
                    similarityMatrix[i][j] = 1;
                } else {
                    // The similarity of a sentence with another sentence is the cosine similarity between the vectors
                    similarityMatrix[i][j] = calculateCosineSimilarity(sentenceVectors.get(i), sentenceVectors.get(j));
                }
            }
        }

        // Initialize a score matrix with equal scores for all sentences.
        double[] scores = new double[sentences.size()];
        Arrays.fill(scores, 1.0);

        // For each iteration:
        //    - Update the score of each sentence based on the scores of the sentences it's connected to in the similarity matrix.
        //    - Apply the damping factor to the scores.

        // For each iteration:
        for (int iter = 0; iter < ITERATIONS; iter++) {
            // Create a new array to store the updated scores
            double[] newScores = new double[sentences.size()];

            // Update the score of each sentence
            for (int i = 0; i < sentences.size(); i++) {
                for (int j = 0; j < sentences.size(); j++) {
                    if (i != j) {
                        newScores[i] += DAMPING_FACTOR * similarityMatrix[i][j] * scores[j];
                    }
                }
                newScores[i] += (1 - DAMPING_FACTOR);
            }

            // Replace the old scores with the new scores
            scores = newScores;
        }

        // After all iterations, sort the sentences by their scores in descending order.
        List<Integer> indices = IntStream.range(0, sentences.size()).boxed().collect(Collectors.toList());

        // Create a final copy of scores
        final double[] finalScores = scores;

        // Sort the indices by the scores in descending order
        indices.sort((i, j) -> Double.compare(finalScores[j], finalScores[i]));

        // Map the sorted indices back to sentences
        Map<String, Double> sortedSentences = indices.stream()
            .collect(Collectors.toMap(i -> sentences.get(i), i -> finalScores[i], (a, b) -> a, LinkedHashMap::new));

        // Return the top `summaryLength` sentences.
        return sortedSentences.entrySet().stream()
            .limit(summaryLength)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    /**
     * Returns the sentences.
     *
     * @return A list of sentences.
    */

    public List<String> getSentences() {
        return sentences;
    }

    /**
     * Calculates the cosine similarity between two vectors.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     * @return A double representing the cosine similarity between the two vectors.
    */

    private double calculateCosineSimilarity(Map<String, Integer> vector1, Map<String, Integer> vector2) {
        // The cosine similarity between two vectors is the dot product of the vectors divided by the product of the norms of the vectors.
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
    
        // Loop through the words in the first vector and calculate the dot product and the norm of the first vector
        for (String word : vector1.keySet()) {
            // The dot product of two vectors is the sum of the products of the corresponding entries of the two vectors.
            dotProduct += vector1.get(word) * vector2.getOrDefault(word, 0);
            // The norm of a vector is the square root of the sum of the squares of the entries of the vector.
            normA += Math.pow(vector1.get(word), 2);
        }
        
        // Loop through the words in the second vector and calculate the norm of the second vector
        for (int freq : vector2.values()) {
            // The norm of a vector is the square root of the sum of the squares of the entries of the vector.
            normB += Math.pow(freq, 2);
        }
    
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}

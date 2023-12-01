package com.slidecreatorvba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * FrequencyRank class is a class that takes the output of the FileInputHandler class (a list of sentences)
 * and ranks them based on a frequency rank algorithm.
 *
 * Future Improvements:
 * 1. Stemming/Lemmatization: Implement stemming or lemmatization to reduce words to their base or root form.
 *    This will require the use of a library like Stanford CoreNLP or Apache OpenNLP.
 * 2. Term Frequency-Inverse Document Frequency (TF-IDF): Instead of just counting word frequencies, use TF-IDF
 *    to determine the importance of words in your sentences. This will require a bit more work, as you'd need to
 *    calculate the term frequency and the inverse document frequency for each word.
*/

public class FrequencyRank {

    private List<String> sentences;
    // Define a list of stopwords to remove from the sentences
    private List<String> stopwords = Arrays.asList("il", "la", "i", "gli", "le", "di", "a", "da", "in", "su", "per", "con", "e", "o", "ma", "già", "non", "ne", "anche", "pure", "ancora", "sempre", "mai", "poi", "se", "quindi", "ma", "perché", "come", "quando", "dove", "chi", "che", "cosa", "cui", "quanto");

    /**
     * Constructor for FrequencyRank class.
     *
     * @param sentences The list of sentences.
    */

    public FrequencyRank(List<String> sentences) {
        this.sentences = sentences;
    }

    /**
     * Preprocesses a sentence by converting it to lowercase, removing punctuation, numbers, and stopwords, and trimming whitespace.
     *
     * @param sentence The sentence to preprocess.
     * @return The preprocessed sentence.
    */

    private String preprocess(String sentence) {
        // Convert to lowercase
        String cleaned = sentence.toLowerCase();
        // Remove punctuation, numbers, and text within parentheses
        cleaned = cleaned.replaceAll("\\(.*?\\)|[^a-zA-Z\\s]", "");
        // Split into words
        List<String> words = Arrays.asList(cleaned.split("\\s+"));
        // Remove stopwords
        words = words.stream().filter(word -> !stopwords.contains(word)).collect(Collectors.toList());
        // Join the words back into a sentence
        cleaned = String.join(" ", words);
        return cleaned;
    }

    /**
     * Returns the top sentences based on the summary length.
     *
     * @param summaryLength The length of the summary.
     * @return A list of sentences.
    */

    public Map<String, Double> rankSentences(int summaryLength) {
    // Create a map to store the word frequencies
    Map<String, Integer> wordFrequencies = new HashMap<>();

    // Create a method to count the word frequencies
    for (String sentence : sentences) {
        // Preprocess the sentence
        String preprocessed = preprocess(sentence);
        // Split the preprocessed sentence into words
        List<String> words = Arrays.asList(preprocessed.split("\\s+"));

        // Create a list of 2-grams from the words
        List<String> ngrams = new ArrayList<>();
        for (int i = 0; i < words.size() - 1; i++) {
            ngrams.add(words.get(i) + " " + words.get(i + 1));
        }

        // Loop through the n-grams and add them to the map
        for (String ngram : ngrams) {
            wordFrequencies.put(ngram, wordFrequencies.getOrDefault(ngram, 0) + 1);
        }
    }

    // Create a map to store the sentence scores
    Map<String, Double> sentenceScores = new HashMap<>();

    // Create a method to score the sentences
    for (String sentence : sentences) {
        // Split the sentence into words, remove stopwords, and convert to lowercase to ensure that "The" and "the" are counted as the same word
        List<String> words = Arrays.stream(sentence.toLowerCase().split("\\s+"))
            .filter(word -> !stopwords.contains(word))
            .collect(Collectors.toList());

        // Create a list of 2-grams from the words
        List<String> ngrams = new ArrayList<>();
        for (int i = 0; i < words.size() - 1; i++) {
            ngrams.add(words.get(i) + " " + words.get(i + 1));
        }

        double score = 0;

        // Loop through the n-grams and add the score to the map
        for (String ngram : ngrams) {
            if (wordFrequencies.containsKey(ngram)) {
                score += wordFrequencies.get(ngram);
            }
        }
        sentenceScores.put(sentence, score);
    }

    // Return the top sentences based on the summary length
    return sentenceScores.entrySet().stream()
                         .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                         .limit(summaryLength)
                         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
}

    /**
     * Returns the sentences.
     *
     * @return A list of sentences.
    */

    // Create a method to get the sentences
    public List<String> getSentences(){
        return this.sentences;
    }

}

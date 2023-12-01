package com.slidecreatorvba;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents a hybrid ranking algorithm that combines the results of the FrequencyRank and TextRank algorithms.
 * The final score for each sentence is a weighted average of the scores from the FrequencyRank and TextRank algorithms.
*/

public class HybridRank {
    private List<String> sentences;
    private FrequencyRank frequencyRank;
    private TextRank textRank;
    private double frequencyWeight;

    /**
     * Constructs a new HybridRank object.
     * @param sentences The sentences to be ranked.
    */

    public HybridRank(List<String> sentences) {
        this.sentences = sentences;
        this.frequencyRank = new FrequencyRank(sentences);
        this.textRank = new TextRank(sentences);
        this.frequencyWeight = 0.5;
    }

    /**
     * Returns a summary of the given length. The summary is created by ranking the sentences using a hybrid of the FrequencyRank and TextRank algorithms.
     * @param summaryLength The number of sentences to include in the summary.
     * @return A list of sentences in the summary.
    */

    public List<String> getSummary(int summaryLength) {

        // Check if the summary length is valid (> 1 and less than the number of sentences of the .txt file)
        if (summaryLength < 1 || summaryLength > sentences.size()) {
            throw new IllegalArgumentException("The summary length must be greater than 0 and less than the number of sentences in the .txt file.");
        }

        // Get the ranked sentences from both algorithms
        Map<String, Double> frequencyRankedSentences = frequencyRank.rankSentences(summaryLength);
        Map<String, Double> textRankedSentences = textRank.rankSentences(summaryLength);

        // Combine the ranked sentences
        Map<String, Double> combined = new HashMap<>();
        for (String sentence : sentences) {
            double frequencyScore = frequencyRankedSentences.getOrDefault(sentence, 0.0);
            double textScore = textRankedSentences.getOrDefault(sentence, 0.0);
            double combinedScore = frequencyWeight * frequencyScore + (1 - frequencyWeight) * textScore;
            combined.put(sentence, combinedScore);
        }

        // Sort the sentences based on their combined scores
        List<String> summary = combined.entrySet().stream()
            .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        // Return the top sentences based on the summary length as a list
        return postprocessSummary(summary.subList(0, Math.min(summaryLength, summary.size())));
    }

    /**
     * Returns the postprocessed summary. The postprocessing removes text between brackets () [] {} and &lt;&gt; and removes the brackets.
     * @param summary The summary to be postprocessed.
     * @return The postprocessed summary.
    */

    public List<String> postprocessSummary(List<String> summary) {
        // A method that postprocesses the summary removing text between brackets () [] {} and <> and removes the brackets --> input is a list of sentences and output is a list of sentences
        List<String> postprocessedSummary = new ArrayList<>();
        for (String sentence : summary) {
            String postprocessedSentence = sentence.replaceAll("\\([^\\.]*?\\)|\\[[^\\.]*?]|\\{[^\\.]*?}|<[^\\.]*?>", "").trim();
            // Replace double spaces with a single space
            postprocessedSentence = postprocessedSentence.replaceAll("\\s{2,}", " ");
            // Remove spaces before punctuation
            postprocessedSentence = postprocessedSentence.replaceAll("\\s+(?=[.,;:!?])", "");
            postprocessedSummary.add(postprocessedSentence);
        }
        return postprocessedSummary;
    }

}
package com.slidecreatorvba;

import java.util.ArrayList;
import java.util.List;

/**
 * PowerPoint class represents a PowerPoint with a list of slides.
 * Each PowerPoint object has a list of slides that can be set and retrieved.
*/

public class PowerPoint {

    // A PowerPoint object is a list of slides
    private List<Slide> slides;

    /**
     * Constructor for PowerPoint class.
     * Accepts the number of slides to create and adds them to the PowerPoint.
     *
     * @param sentences The list of sentences.
     * @param numSlides The number of slides to create.
    */

    public PowerPoint(List<String> sentences, int numSlides) {
        this.slides = new ArrayList<>();

        // Check if the number of slides is valid
        if (numSlides > sentences.size() / 3) {
            throw new IllegalArgumentException("The number of desired slides exceeds the maximum number of slides that can be created from the summary.");
        }

        // Create the summary
        HybridRank summary = new HybridRank(sentences);

        // Add the slides to the PowerPoint
        addSlides(summary.getSummary(numSlides * 3));
    }

    /**
     * Adds slides to the PowerPoint.
     * Each slide consists of three sentences from the summary.
     * The first sentence is the title and the next two are the text.
     *
     * @param summary The list of sentences.
    */

    public void addSlides(List<String> summary) {
        int numSlides = (int) Math.ceil((double) summary.size() / 3);
    
        // Create each slide and add it to the PowerPoint
        for (int i = 0; i < numSlides; i++) {
            int start = i * 3;
            Slide slide = new Slide();
    
            StringBuilder slideText = new StringBuilder();
            // Add up to three sentences to the slide text
            for (int j = start; j < Math.min(start + 3, summary.size()); j++) {
                slideText.append(summary.get(j)).append(". ");
            }
    
            slide.setTitle(summary.get(start)); // Set the title to the first sentence of the block
            slide.setText(slideText.toString().trim()); // Remove the trailing space
    
            this.slides.add(slide);
        }
    }

    /**
     * Returns the number of slides in the PowerPoint.
     *
     * @return The number of slides.
    */

    public int getNumberOfSlides(){
        return this.slides.size();
    }

    /**
     * Returns the slide at the specified index.
     *
     * @param index The index of the slide.
     * @return The slide at the specified index.
    */

    public Slide getSlide(int index) {
        return this.slides.get(index);
    }

    /**
     * Prints the title and text of each slide in the PowerPoint.
    */

    public void printSlides() {
        for (Slide slide : this.slides) {
            System.out.println(slide.getTitle());
            System.out.println(slide.getText());
            System.out.println("-------------------------------");
        }
    }
}
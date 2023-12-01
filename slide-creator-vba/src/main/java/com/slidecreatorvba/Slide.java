package com.slidecreatorvba;

    /**
     * Slide class represents a slide with a title and text.
     * Each Slide object has a title and text that can be set and retrieved.
    */

public class Slide {

    private String title;
    private String text;

    /**
     * Constructor for Slide class.
     *
     * @param title The title of the slide.
     * @param text The text of the slide.
    */

    public Slide(String title, String text) {
        this.title = title;
        this.text = text;
    }

    /**
     * Constructor for Slide class.
     * Initializes the title and text of the slide to empty strings.
    */

    public Slide() {
        this.title = "";
        this.text = "";
    }

    /**
     * Returns the title of the slide.
     *
     * @return A string representing the title of the slide.
    */

    public String getTitle(){
        return this.title;
    }

    /**
     * Returns the text of the slide.
     *
     * @return A string representing the text of the slide.
    */

    public String getText(){
        return this.text;
    }

    /**
     * Sets the title of the slide.
     *
     * @param title The title of the slide.
    */

    public void setTitle(String title) {
        // Split the title into words
        String[] words = title.split("\\s+");
    
        // Initialize a StringBuilder to build the new title
        StringBuilder newTitle = new StringBuilder();
    
        // Add words to the new title until it reaches 8 words or a word ends with a punctuation sign
        for (int i = 0; i < Math.min(8, words.length); i++) {
            newTitle.append(words[i]);
            if (i < Math.min(8, words.length) - 1) {
                newTitle.append(" ");
            }
            if (words[i].matches(".*[.,;?!]")) {
                break;
            }
        }
    
        // Set the title of the slide
        this.title = newTitle.toString().toUpperCase();
    }

    /**
     * Sets the text of the slide.
     *
     * @param text The text of the slide.
    */

    public void setText(String text) {
        this.text = text;
    }

}

package com.pr1.game;

public class Option {
    private String description;
    private int destination;

    public Option(String description, int destination) {
        this.description = description;
        this.destination = destination;
    }

    public int getDestination() {
        return this.destination;
    }

    public String getDescription() {
        return this.description;
    }
}

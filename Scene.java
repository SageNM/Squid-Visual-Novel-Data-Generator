package com.pr1.game;

import java.util.ArrayList;

public class Scene {

    private int id;
    private String[] locations;
    private String[] squids;
    private ArrayList<String[]> dialogue = new ArrayList<String[]>();
    private Option[] options;
    private int status = 0;
    private int textStatus = 0;

    public Scene(int id, String[] locations, String[] squids, String[] dialogue, Option[] options) {
        // locations, squids, and dialogue should all be of the same length
        this.id = id;
        this.locations = locations;
        this.squids = squids;
        this.options = options;
        // Breaking down dialogue by line
        for (String chunk: dialogue) {
            this.dialogue.add(chunk.split("\n"));
        }
    }

    public boolean advance() {
        // If moved past dialogue, just return options
        if (status >= dialogue.size() || ((textStatus + 1) >= dialogue.get(status).length && status == dialogue.size() - 1)) {
            // made it to options
            return true;
        }

        String[] currentDialogue = dialogue.get(status);
        // if at last dialogue of section, advance section
        if (textStatus + 1 >= currentDialogue.length) {
            status++;
            textStatus = 0;
            currentDialogue = dialogue.get(status);
        } else {
            textStatus++;
        }
        return false;
    }

    public String[] getCurrentScene() {
        // For location, squid, and dialogue
        String[] sceneData = new String[3];

        if (status >= dialogue.size()) {
            // made it to options
            String[] currentOptions = new String[options.length];
            int i = 0;
            for (Option choice: options) {
                currentOptions[i] = choice.getDescription();
                i++;
            }
            return currentOptions;
        }

        String[] currentDialogue = dialogue.get(status);
        sceneData[0] = locations[status];
        sceneData[1] = squids[status];
        sceneData[2] = currentDialogue[textStatus];

        return sceneData;
    }

    public int getId() {
        return this.id;
    }

    public Option[] getOptions() {
        return this.options;
    }
}

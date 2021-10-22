package com.pr1.game;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
    private Document plot;
    private Scene[] scenes;

    public XMLParser(String filename) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            plot = builder.parse(new File(filename));
            // Making scenes from plot
            Element root = plot.getDocumentElement();
            NodeList choices = root.getElementsByTagName("choice");
            scenes = new Scene[choices.getLength()];
            // Cycling through each choice
            for (int i = 0; i < choices.getLength(); i++) {
                Node choice = choices.item(i);
                if (choice instanceof Element) {
                    Element choiceElement = (Element) choice;
                    // Getting ID
                    String idString = choiceElement.getElementsByTagName("id").item(0).getTextContent();
                    int id = Integer.parseInt(idString);
                    // Getting locations, squids, and dialogue
                    NodeList locationNodes = choiceElement.getElementsByTagName("location");
                    NodeList squidNodes = choiceElement.getElementsByTagName("squid");
                    NodeList dialogueNodes = choiceElement.getElementsByTagName("dialogue");
                    String[] locations = new String[locationNodes.getLength()];
                    String[] squids = new String[squidNodes.getLength()];
                    String[] dialogues = new String[dialogueNodes.getLength()];
                    for (int j = 0; j < locationNodes.getLength(); j++) {
                        locations[j] = locationNodes.item(j).getTextContent();
                        squids[j] = squidNodes.item(j).getTextContent();
                        dialogues[j] = dialogueNodes.item(j).getTextContent();
                    }
                    // Parsing options
                    NodeList optionNodes = choiceElement.getElementsByTagName("option");
                    Option[] options = new Option[optionNodes.getLength()];
                    for (int j = 0; j < options.length; j++) {
                        Node option = optionNodes.item(j);
                        if (option instanceof Element) {
                            Element optionElement = (Element) option;
                            String description = optionElement.getElementsByTagName("description").item(0).getTextContent();
                            String toIdString = optionElement.getElementsByTagName("to-id").item(0).getTextContent();
                            int toId = Integer.parseInt(toIdString);
                            options[j] = new Option(description, toId);
                        }
                    }
                    scenes[i] = new Scene(id, locations, squids, dialogues, options);
                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Scene[] getScenes() {
        return scenes;
    }
}

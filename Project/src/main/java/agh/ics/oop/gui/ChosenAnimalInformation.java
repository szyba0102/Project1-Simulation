package agh.ics.oop.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import agh.ics.oop.Animal;
import java.util.Arrays;


public class ChosenAnimalInformation {


    private final Text childrenText = new Text();
    private final Text ancestorsText = new Text();
    private final Text dayOfDeathText = new Text();
    private final Text genomeOfAnimal = new Text();

    public ChosenAnimalInformation(){}

    // update box with chosen animal informations
    public void updateInformation(Animal animal){
        childrenText.setText(String.valueOf(animal.getQuantityOfChildrenAfterChoice()));
        ancestorsText.setText(String.valueOf(animal.getAnimalDescendantCounter().quantityOfDescendents()));
        if(animal.getIsAlive()){
            dayOfDeathText.setText("alive");
        }
        else {dayOfDeathText.setText(String.valueOf(animal.getLifespan()));}
    }
    // update text with animals genome
    public void updateGenomeOFChosenAnimal(Animal animal){
        genomeOfAnimal.setText(Arrays.toString(animal.getGenotype()));
    }

    public void updateGenomUchosen(){
        genomeOfAnimal.setText("not chosen");
    }

    // creates the box that contain all teh information
    public VBox createBox(){
        HBox allInformation = new HBox();

        VBox children = new VBox();
        children.setSpacing(10);
        children.setPadding(new Insets(10));
        Label childernLable = new Label("Quantity of children");

        children.getChildren().addAll(childernLable,childrenText);
        children.setAlignment(Pos.CENTER);

        VBox descendant = new VBox();
        descendant.setSpacing(10);
        descendant.setPadding(new Insets(10));
        Label descendantLable = new Label("Quantity of descendant");

        descendant.getChildren().addAll(descendantLable,ancestorsText);
        descendant.setAlignment(Pos.CENTER);

        VBox dayOfDeath = new VBox();
        dayOfDeath.setSpacing(10);
        dayOfDeath.setPadding(new Insets(10));
        Label dayOfDeathLable = new Label("Era of death");
        dayOfDeath.getChildren().addAll(dayOfDeathLable,dayOfDeathText);
        dayOfDeath.setAlignment(Pos.CENTER);

        allInformation.getChildren().addAll(children,descendant,dayOfDeath);
        allInformation.setAlignment(Pos.CENTER);
        allInformation.setVisible(true);

        VBox finalBox = new VBox();
        Label genomOFChosenAnimalLabel = new Label("GENOME OF CHOSEN ANIMAL");
        finalBox.setAlignment(Pos.CENTER);
        genomeOfAnimal.setText("not chosen");
        finalBox.setVisible(true);
        finalBox.getChildren().addAll(allInformation,genomOFChosenAnimalLabel,genomeOfAnimal);
        return finalBox;
    }

}

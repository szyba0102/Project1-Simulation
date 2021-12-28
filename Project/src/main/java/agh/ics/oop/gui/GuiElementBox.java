package agh.ics.oop.gui;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import agh.ics.oop.*;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

// object creates boxes that represents objects on  map (grass and animals)
public class GuiElementBox extends Node {
    private final App app;

    public GuiElementBox(App app){
        this.app = app;
    }

    public VBox createObject(IMapElement object,double x, double y) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(object.getNameOfPicture()));
        } catch (FileNotFoundException e) {
            System.out.println("File doesnt exists");
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(x-2);
        imageView.setFitHeight(y-2);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView);
        vbox.setAlignment(Pos.CENTER);

        // if box is clicekd new animal is chosen
        // if mouse enter box genome of animal is printed
        // if mouse leaves box genome disappears
        if(object instanceof Animal){
            vbox.setOnMouseClicked(event -> app.updateChosenAnimal((Animal) object));
            vbox.setOnMouseEntered(event -> app.updateGenomeOfChosenAnimal((Animal) object));
            vbox.setOnMouseExited(event -> app.updateUnchosenGenomeOfAnimal((Animal) object));
        }
        return vbox;
    }



}
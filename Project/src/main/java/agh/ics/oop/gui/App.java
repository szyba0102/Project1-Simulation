package agh.ics.oop.gui;
import agh.ics.oop.*;
import agh.ics.oop.AbstractSimulationEngine;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.SimulationEngineMagic;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.chart.*;



public class App extends Application {
    private AbstractWorldMap mapSavanna;
    private AbstractWorldMap mapRec;
    private AbstractSimulationEngine engineSavanna;
    private AbstractSimulationEngine engineRec;
    private Thread engineThreadSavanna;
    private Thread engineThreadRec;
    GridPane gridPaneSavanna = new GridPane();
    GridPane gridPaneRec = new GridPane();
    Text textSavanna = new Text();
    Text textRec = new Text();
    TextField namesOfFilesText;
    String fileName;
    // boxes with informations about chosen animals
    private VBox allInformationForSavanna;
    private VBox allInformationForRec;
    // objects that create the above boxes and update them
    private final ChosenAnimalInformation chosenAnimalInformationCreatorForSavanna = new ChosenAnimalInformation();
    private final ChosenAnimalInformation chosenAnimalInformationCreatorForRec = new ChosenAnimalInformation();
    // objects that create and update charts
    private ChartForMaps chartForSavannaCreator ;
    private ChartForMaps chartForRecCreator;
    private Chart chartForSavanna;
    private Chart chartForRec;
    // text fields that informs user about adding 5 new animals
    private final Text magicSavanna = new Text();
    private final Text magicRec = new Text();
    private final Rectangle2D screenbounds =Screen.getPrimary().getVisualBounds();

    @Override
    public void init(){
    }

    @Override
    public void start(Stage primaryStage){
        // initial scene
        Button buttonStart = new Button("Start");

        Label widthLabel = new Label("WIDTH");
        TextField widthText = new TextField("20");

        Label heightLabel = new Label("HEIGHT");
        TextField heightText = new TextField("20");

        Label startEnergyLabel = new Label("START ENERGY");
        TextField startEnergyText = new TextField("90");

        Label moveEnergyLabel = new Label("MOVE ENERGY");
        TextField moveEnergyText = new TextField("1");

        Label plantEnergyLabel = new Label("PLANT ENERGY");
        TextField plantEnergyText = new TextField("20");

        Label jungleratioLabel = new Label("JUNGLE RATIO");
        TextField jungleRatioText = new TextField("0.3");

        Label quantityOfAnimalsLabel = new Label("QUANTITY OF ANIMALS");
        TextField quantityOfAnimalsText = new TextField("50");

        Label namesOfFiles = new Label("NAME OF FILE");
        Random rand = new Random();

        // example of name of the file that will be created for this simulation statistical data
        int x = rand.nextInt(1000000);
        namesOfFilesText = new TextField("exmapleOfName"+x);

        Label magic = new Label("Options: NORMAL | MAGIC -> (when map contains 5 animals, 5 new will magically appear)");
        magic.setPadding(new Insets(15,10,15,10));

        Label magicForSavana = new Label("CHOSE OPTION FOR SAVANNA");
        TextField magicForSavanaText = new TextField("MAGIC");

        Label magicForRec = new Label("CHOSE OPTION FOR RECTANGULAR");
        TextField magicForRecText = new TextField("MAGIC");


        VBox vbox = new VBox();
        vbox.getChildren().addAll(widthLabel,widthText,heightLabel,heightText,startEnergyLabel,startEnergyText,moveEnergyLabel,moveEnergyText,plantEnergyLabel,plantEnergyText,jungleratioLabel,jungleRatioText,quantityOfAnimalsLabel,quantityOfAnimalsText,namesOfFiles,namesOfFilesText,magic,magicForSavana,magicForSavanaText,magicForRec,magicForRecText,buttonStart);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // getting data from initial scene
        buttonStart.setOnAction((event) -> {
            int widthForMaps = Integer.parseInt(widthText.getText());
            int heightForMaps = Integer.parseInt(heightText.getText());
            int startEnergy = Integer.parseInt(startEnergyText.getText());
            int moveEnergy = Integer.parseInt(moveEnergyText.getText());
            int plantEnergy = Integer.parseInt(plantEnergyText.getText());
            double jungleRatio = Double.parseDouble(jungleRatioText.getText());
            int quantityofAnimals = Integer.parseInt(quantityOfAnimalsText.getText());
            String typeOfEngineSavanna = magicForSavanaText.getText();
            String typeOfEngineRec = magicForRecText.getText();
            fileName = namesOfFilesText.getText();

            // two new maps are being created with data obtained from user
            mapSavanna = new Savanna(widthForMaps,heightForMaps,jungleRatio,startEnergy,moveEnergy,plantEnergy);
            mapRec = new RectangularMap(widthForMaps,heightForMaps,jungleRatio,startEnergy,moveEnergy,plantEnergy);

            // engines are being created depending on what option was chosen by user
            // if user will choose option that doesn't exist simulation won't start and information about it will be printed
            if(typeOfEngineSavanna.equals("NORMAL")){
                engineSavanna = new SimulationEngine(mapSavanna,quantityofAnimals,this,startEnergy,moveEnergy,plantEnergy,gridPaneSavanna);}
            else if(typeOfEngineSavanna.equals("MAGIC")){
                engineSavanna = new SimulationEngineMagic(mapSavanna,quantityofAnimals,this,startEnergy,moveEnergy,plantEnergy,gridPaneSavanna);}
            else{
                System.out.println("OPTION DOESNT EXISTS!!!");
                return;};

            if(typeOfEngineRec.equals("NORMAL")){
                engineRec = new SimulationEngine(mapRec,quantityofAnimals,this,startEnergy,moveEnergy,plantEnergy,gridPaneRec);}
            else if(typeOfEngineRec.equals("MAGIC")){
                engineRec = new SimulationEngineMagic(mapRec,quantityofAnimals,this,startEnergy,moveEnergy,plantEnergy,gridPaneRec);}
            else{
                System.out.println("OPTION DOESNT EXISTS!!!");
                return;};

            // new threads are being created
            engineThreadSavanna = new Thread(engineSavanna);
            engineThreadRec = new Thread(engineRec);
            // new scene is being created
            Scene scene2 = StartOfSimulation();
            primaryStage.setScene(scene2);
            primaryStage.show();
            primaryStage.setFullScreen(true);
        });

    }

    public Scene StartOfSimulation(){
        // two charts for each map are being created
        chartForSavannaCreator = new ChartForMaps(mapSavanna);
        chartForRecCreator = new ChartForMaps(mapRec);
        String labels = "quantityOfAnimal;quantityOfGrass;AvgEnergy;AvgLifespan;AvgQuantityOfChildren";
        mapSavanna.createDataFile(fileName,labels);
        mapRec.createDataFile(fileName,labels);
        createCharts();

        // all the elements of new scene are being created
        // buttons STOP stops simulation on the specific map
        // buttons START restarts simulation
        Button buttonStartSavana = new Button("Start");
        buttonStartSavana.setOnAction((event) -> {
            engineSavanna.restart();
        });

        Button buttonStopSavana = new Button("STOP");
        buttonStopSavana.setOnAction((event) -> {
            engineSavanna.stop();
        });

        Button buttonSaveSavana = new Button("SAVE");
        buttonSaveSavana.setOnAction((event) -> {
            mapSavanna.getDataFile().addNewData(mapSavanna.getStatisticsData().getDataList());
            mapSavanna.getStatisticsData().clearList();
        });

        // when mouse enters box, animal with dominant genome are marked with X
        VBox dominantAnimalsSavanna = new VBox();
        Label dominantAnimalsSavannaLabel1 = new Label("show animals ");
        Label dominantAnimalsSavannaLabel2 = new Label("with dominant genome");
        dominantAnimalsSavanna.getChildren().addAll(dominantAnimalsSavannaLabel1,dominantAnimalsSavannaLabel2);
        dominantAnimalsSavanna.setAlignment(Pos.CENTER);
        dominantAnimalsSavanna.setOnMouseEntered(event -> changeMap(mapSavanna.searchingForAnimalsWithDominantGenome(),gridPaneSavanna,mapSavanna));
        dominantAnimalsSavanna.setOnMouseExited(event -> createMap(mapSavanna,gridPaneSavanna));

        Button buttonStartRec = new Button("Start");
        buttonStartRec.setOnAction((event) -> {
            engineRec.restart();
        });

        Button buttonStopRec = new Button("STOP");
        buttonStopRec.setOnAction((event) -> {
            engineRec.stop();
        });

        Button buttonSaveRec = new Button("SAVE");
        buttonSaveRec.setOnAction((event) -> {
            mapRec.getDataFile().addNewData(mapRec.getStatisticsData().getDataList());
            mapRec.getStatisticsData().clearList();
        });

        // when mouse enters box animal with dominant genome are marked with X
        VBox dominantAnimalsRec = new VBox();
        Label dominantAnimalsRecLabel1 = new Label("show animals ");
        Label dominantAnimalsRecLabel2 = new Label("with dominant genome");
        dominantAnimalsRec.getChildren().addAll(dominantAnimalsRecLabel1,dominantAnimalsRecLabel2);
        dominantAnimalsRec.setAlignment(Pos.CENTER);
        dominantAnimalsRec.setOnMouseEntered(event -> changeMap(mapRec.searchingForAnimalsWithDominantGenome(),gridPaneRec,mapRec));
        dominantAnimalsRec.setOnMouseExited(event -> createMap(mapRec,gridPaneRec));


        Label label1 = new Label("SAVANNA");
        Label label1_2 = new Label("(when animal get to the edge it appears on the opposite side of the map)");

        Label label2 = new Label("RECTANGULAR");
        Label label2_2 = new Label("(when animal get to the edge it loses round)");
        Text dominant1 = new Text("Dominant genome");
        Text dominant2 = new Text("Dominant genome");

        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(10,10,30,10));
        hbox1.setSpacing(20);
        hbox1.getChildren().addAll(buttonStartSavana,buttonStopSavana,buttonSaveSavana,dominantAnimalsSavanna);
        hbox1.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10,10,30,10));
        hbox2.setSpacing(20);
        hbox2.getChildren().addAll(buttonStartRec,buttonStopRec,buttonSaveRec,dominantAnimalsRec);
        hbox2.setAlignment(Pos.CENTER);


        VBox vbox1 = new VBox();

        vbox1.getChildren().addAll(label1,label1_2,gridPaneSavanna,hbox1,dominant1,textSavanna,allInformationForSavanna,magicSavanna);
        vbox1.setAlignment(Pos.CENTER);

        VBox vbox2 = new VBox();

        vbox2.getChildren().addAll(label2,label2_2,gridPaneRec,hbox2,dominant2,textRec,allInformationForRec,magicRec);
        vbox2.setAlignment(Pos.CENTER);

        HBox maps = new HBox();
        maps.getChildren().addAll(vbox1,vbox2);



        VBox mapsWithLegend = new VBox();
        Label legendLabel = new Label("LEGEND: (Animals energy between)");
        mapsWithLegend.getChildren().addAll(legendLabel,createLegend(),maps);
        mapsWithLegend.setAlignment(Pos.CENTER);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(chartForSavanna,mapsWithLegend,chartForRec);

        hbox.fillHeightProperty();
        engineThreadSavanna.start();
        engineThreadRec.start();

        Scene scene = new Scene(hbox, 1500, 1000);
        return scene;

    }

    // prints message that magic happened (5 new animals appeared on the map) and how many times it happened
    public void updateMagic(IWorldMap map, int counter){
        if(map instanceof Savanna){
            magicSavanna.setText("Magic happened: " + counter);
        }
        else{
            magicRec.setText("Magic happened: " + counter);
        }

    }

    // after choosing animal to being followed engine is informed about it
    public void updateChosenAnimal(Animal animal){
        if(animal.getMap() instanceof Savanna){
            engineSavanna.setChosenAnimal(animal);
        }
        else{
            engineRec.setChosenAnimal(animal);
        }
        updateChosenAnimalInformation(animal);
    }

    // informing object that prints information about chosen animal, about the changes
    public void updateChosenAnimalInformation(Animal animal){
        if(animal.getMap() instanceof Savanna){
            chosenAnimalInformationCreatorForSavanna.updateInformation(animal);}
        else{
            chosenAnimalInformationCreatorForRec.updateInformation(animal);
        }
    }

    // informing object that prints chosen animal genome about the changes
    public void updateGenomeOfChosenAnimal(Animal animal){
        if(animal.getMap() instanceof Savanna){
            chosenAnimalInformationCreatorForSavanna.updateGenomeOFChosenAnimal(animal);}
        else{
            chosenAnimalInformationCreatorForRec.updateGenomeOFChosenAnimal(animal);
        }
    }
    // informing object that prints chosen animal genome that mouse doesn't point on the animal anymore
    public void updateUnchosenGenomeOfAnimal(Animal animal){
        if(animal.getMap() instanceof Savanna){
            chosenAnimalInformationCreatorForSavanna.updateGenomUchosen();}
        else{
            chosenAnimalInformationCreatorForRec.updateGenomUchosen();
        }
    }

    // informs object responsible for csv files to calculate averaged values
    public void endOfSimulation(IWorldMap map){
        map.getDataFile().endOfSimulation();
    }

    public void createCharts(){
        chartForSavanna = chartForSavannaCreator.createChart();
        chartForRec = chartForRecCreator.createChart();
        allInformationForSavanna = chosenAnimalInformationCreatorForSavanna.createBox();
        allInformationForRec = chosenAnimalInformationCreatorForRec.createBox();
    }

    // updating charts data
    public void updateSeries(StatisticsData data,int[] dominant, IWorldMap map, int day) {
        if(map instanceof Savanna){
            textSavanna.setText(Arrays.toString(dominant));
            chartForSavannaCreator.updateSeries(data,day);
        }
        else
        {textRec.setText(Arrays.toString(dominant));
            chartForRecCreator.updateSeries(data,day);}
    }
    // this method creates Xs that mark animal with dominant genome
    public void changeMap(List<Animal> animalDominant, GridPane gridPane, IWorldMap map) {
        for(Animal animal: animalDominant){
            Vector2d position = animal.getLocation();
            VBox newElement = new VBox();
            Image image = null;
            try{
                image = new Image(new FileInputStream("src/main/resources/animalDominant.png"));}
            catch (Exception e){
                System.out.println("file doesnt exsist");
            }

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(screenbounds.getWidth()/(map.getWidth()+1)*((float)3/10));
            imageView.setFitHeight(screenbounds.getHeight()/(map.getHeight()+1)*((float) 3/5));
            newElement.getChildren().add(imageView);
            int x = position.getX() + 1;
            int y = map.getHeight() - position.getY();
            GridPane.setConstraints(newElement, x,y);
            GridPane.setHalignment(newElement, HPos.CENTER);
            gridPane.add(newElement,x,y);
        }}
    // method creates and prints map
    public void createMap(AbstractWorldMap map, GridPane gridPane) {


        gridPane.setGridLinesVisible(false);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);

        gridPane.setPadding(new Insets(20, 20, 20, 20));

        int x = map.getWidth()+1;
        int y = map.getHeight()+1;

        int xBottom = 0;
        int yBottom = 0;

        for (int i = 0; i < x; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(screenbounds.getWidth()/x*((float)3/10)); // width in pixels
            columnConstraints.setPercentWidth(100.0 / x); // percentage of total width
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < y; i++) {
            RowConstraints rowConstraints = new RowConstraints(screenbounds.getHeight()/y*((float) 3/5));
            rowConstraints.setPercentHeight(100.0 / y);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i<y; i++)
            for (int j = 0; j<x; j++)
            {
                String text = "";

                if (i == 0 && j == 0) text = "y/x";
                else if (i == 0) text = String.valueOf(j - 1 + xBottom);
                else if (j == 0) text = String.valueOf(y - i - 1 + yBottom);
                else {
                    if (map.objectAt(new Vector2d(j - 1 + xBottom,y - i - 1 + yBottom )) != null){
                        IMapElement object = map.objectAt(new Vector2d(j - 1 + xBottom,y - i - 1 + yBottom));

                        GuiElementBox creator = new GuiElementBox(this);
                        VBox newElement = creator.createObject(object,screenbounds.getWidth()/x*((float)3/10), screenbounds.getHeight()/y*((float) 3/5));
                        GridPane.setConstraints(newElement, j,i);
                        GridPane.setHalignment(newElement, HPos.CENTER);
                        gridPane.add(newElement,j,i);
                        continue;

                    }

                }
                Label newLabel = new Label(text);
                GridPane.setConstraints(newLabel, j,i);
                GridPane.setHalignment(newLabel, HPos.CENTER);
                gridPane.add(newLabel,j,i);

            }
    }

    public HBox createLegend(){
        Image image1_1 = null;
        Image image1_2 = null;
        Image image2_1 = null;
        Image image2_2 = null;
        Image image3_1 = null;
        Image image3_2 = null;
        Image image4_1 = null;
        Image image4_2 = null;
        Image image5_1 = null;
        Image image5_2 = null;
        Image image5_3 =null;
        try{
            image1_1 = new Image(new FileInputStream("src/main/resources/0.png"));
            image1_2 = new Image(new FileInputStream("src/main/resources/1.png"));
            image2_1 = new Image(new FileInputStream("src/main/resources/2.png"));
            image2_2 = new Image(new FileInputStream("src/main/resources/3.png"));
            image3_1 = new Image(new FileInputStream("src/main/resources/4.png"));
            image3_2 = new Image(new FileInputStream("src/main/resources/5.png"));
            image4_1 = new Image(new FileInputStream("src/main/resources/6.png"));
            image4_2 = new Image(new FileInputStream("src/main/resources/7.png"));
            image5_1 = new Image(new FileInputStream("src/main/resources/8.png"));
            image5_2 = new Image(new FileInputStream("src/main/resources/9.png"));
            image5_3 = new Image(new FileInputStream("src/main/resources/10.png"));}
        catch (Exception e){
            System.out.println("file doesnt exsist");
        }

        VBox vbox1 = new VBox();
        vbox1.setAlignment(Pos.CENTER);
        HBox hbox1_1 = new HBox();
        HBox hbox1_2 = new HBox();
        int x = 15;
        ImageView imageView1_1 = new ImageView(image1_1);
        ImageView imageView1_2 = new ImageView(image1_2);
        imageView1_1.setFitWidth(x);
        imageView1_1.setFitHeight(x);
        imageView1_2.setFitWidth(x);
        imageView1_2.setFitHeight(x);

        Label label1_1 = new Label("-> 0 and 9");
        Label label1_2 = new Label("-> 10 and 19");
        hbox1_1.getChildren().addAll(imageView1_1,label1_1);
        hbox1_2.getChildren().addAll(imageView1_2,label1_2);
        vbox1.getChildren().addAll(hbox1_1,hbox1_2);

        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        HBox hbox2_1 = new HBox();
        HBox hbox2_2 = new HBox();

        ImageView imageView2_1 = new ImageView(image2_1);
        ImageView imageView2_2 = new ImageView(image2_2);
        imageView2_1.setFitWidth(x);
        imageView2_1.setFitHeight(x);
        imageView2_2.setFitWidth(x);
        imageView2_2.setFitHeight(x);
        Label label2_1 = new Label("-> 20 and 29");
        Label label2_2 = new Label("-> 30 and 39");
        hbox2_1.getChildren().addAll(imageView2_1,label2_1);
        hbox2_2.getChildren().addAll(imageView2_2,label2_2);
        vbox2.getChildren().addAll(hbox2_1,hbox2_2);

        VBox vbox3 = new VBox();
        vbox3.setAlignment(Pos.CENTER);
        HBox hbox3_1 = new HBox();
        HBox hbox3_2 = new HBox();

        ImageView imageView3_1 = new ImageView(image3_1);
        ImageView imageView3_2 = new ImageView(image3_2);
        imageView3_1.setFitWidth(x);
        imageView3_1 .setFitHeight(x);
        imageView3_2.setFitWidth(x);
        imageView3_2.setFitHeight(x);
        Label label3_1 = new Label("-> 40 and 49");
        Label label3_2 = new Label("-> 50 and 59");
        hbox3_1.getChildren().addAll(imageView3_1,label3_1);
        hbox3_2.getChildren().addAll(imageView3_2,label3_2);
        vbox3.getChildren().addAll(hbox3_1,hbox3_2);

        VBox vbox4 = new VBox();
        vbox4.setAlignment(Pos.CENTER);
        HBox hbox4_1 = new HBox();
        HBox hbox4_2 = new HBox();

        ImageView imageView4_1 = new ImageView(image4_1);
        ImageView imageView4_2 = new ImageView(image4_2);
        imageView4_1.setFitWidth(x);
        imageView4_1.setFitHeight(x);
        imageView4_2.setFitWidth(x);
        imageView4_2.setFitHeight(x);
        Label label4_1 = new Label("-> 60 and 69");
        Label label4_2 = new Label("-> 70 and 79");
        hbox4_1.getChildren().addAll(imageView4_1,label4_1);
        hbox4_2.getChildren().addAll(imageView4_2,label4_2);
        vbox4.getChildren().addAll(hbox4_1,hbox4_2);

        VBox vbox5 = new VBox();
        vbox5.setAlignment(Pos.CENTER);
        HBox hbox5_1 = new HBox();
        HBox hbox5_2 = new HBox();
        HBox hbox5_3 = new HBox();
        ImageView imageView5_1 = new ImageView(image5_1);
        ImageView imageView5_2 = new ImageView(image5_2);
        ImageView imageView5_3 = new ImageView(image5_3);
        imageView5_1.setFitWidth(x);
        imageView5_1.setFitHeight(x);
        imageView5_2.setFitWidth(x);
        imageView5_2.setFitHeight(x);
        imageView5_3.setFitWidth(x);
        imageView5_3.setFitHeight(x);
        Label label5_1 = new Label("-> 80 and 89");
        Label label5_2 = new Label("-> 90 and 99");
        Label label5_3 = new Label("-> 100+");
        hbox5_1.getChildren().addAll(imageView5_1,label5_1);
        hbox5_2.getChildren().addAll(imageView5_2,label5_2);
        hbox5_3.getChildren().addAll(imageView5_3,label5_3);
        vbox5.getChildren().addAll(hbox5_1,hbox5_2,hbox5_3);

        HBox legend = new HBox();
        legend.setAlignment(Pos.CENTER);
        legend.getChildren().addAll(vbox1,vbox2,vbox3,vbox4,vbox5);
        return legend;
    }
}


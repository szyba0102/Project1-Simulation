package agh.ics.oop.Simulation;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.Animal;
import agh.ics.oop.IWorldMap;
import agh.ics.oop.Vector2d;
import agh.ics.oop.gui.App;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractSimulationEngine implements Runnable{
    protected final IWorldMap map;
    protected final List<Animal> animalList = new ArrayList<>(); // list of animals on the map
    protected final App simulationObserver; // front-end observer
    protected final int startEnergy;
    protected final int moveEnergy;
    protected final int plantEnergy;
    protected volatile boolean pause = false;
    protected volatile boolean exit = true;
    protected final GridPane gridPane;
    protected Animal chosenAnimal; // animal chosen by user to being followed
    protected Random random = new Random();
    private int day = 0;

    public AbstractSimulationEngine(IWorldMap map, int quantity, App observer, int startEnergy, int moveEnergy, int plantEnergy, GridPane gridPane){
        this.map = map;
        this.moveEnergy= moveEnergy;
        this.startEnergy = startEnergy;
        simulationObserver = observer;
        this.plantEnergy = plantEnergy;
        this.gridPane = gridPane;
        for(int i=0; i<quantity; i++) {
            createInitialAnimals();
        }
    }
    abstract void magicHappens();

    // engine creates first animals that will appear on the map (the ones that are being created at the beginning)
    // it creates genotype for them and chooses location
    public void createInitialAnimals(){
        Random rand = new Random();
        int x = rand.nextInt(map.getWidth());
        int y = rand.nextInt(map.getHeight());
        while(map.isOccupied(new Vector2d(x,y))){
            x = rand.nextInt(map.getWidth());
            y = rand.nextInt(map.getHeight());
        }
        Animal animal = new Animal(map,new Vector2d(x,y),startEnergy,startEnergy,moveEnergy,plantEnergy,crateGenome());
        map.place(animal);
        animalList.add(animal);
    }
    // this methods enable engine to add animals that were born on the map
    public void addAnimal(Animal animal1,Animal animal2,Vector2d position, double actualEnergy){
        int[] genes = new int[32];

        if(random.nextInt(2) == 1) {
            Animal temp = animal1;
            animal1 = animal2;
            animal2 = temp;
        }
        double energy1 = animal1.getEnergy();
        double energy2 = animal2.getEnergy();
        int leftIndex;
        leftIndex = (int) (energy1/(energy1+energy2)*32);
        for(int i=0; i<leftIndex; i++){
            genes[i] = animal1.getGeneFromGenotype(i);
        }
        for(int i=leftIndex; i<32; i++){
            genes[i] = animal2.getGeneFromGenotype(i);
        }
        Animal animal = new Animal(map,position,startEnergy,actualEnergy,moveEnergy,plantEnergy,genes);
        animal1.afterBaby(animal);
        animal2.afterBaby(animal);
        map.place(animal);
        animalList.add(animal);
    }

    public void removeBodies(Animal animal){
        animalList.remove(animal);
    } // removes dead animal from list

    // if user choose animal it is updated here
    public void setChosenAnimal(Animal animal){
        if(chosenAnimal != null){
            chosenAnimal.setUnchosen();
        }
        chosenAnimal = animal;
        chosenAnimal.setChosen();
    }
    // creating genome randomly
    public int[] crateGenome(){
        int[] genes = new int[32];
        Random rand = new Random();
        int x = 0;
        int m = 0;
        for(int k = 0;k<7;k++){
            x = rand.nextInt(25+k-m) +1 ;
            for(int i=0; i<x;i++){
                genes[i+m] = k;
            }
            m += x;
        }
        for(int i=m;i<32;i++){
            genes[i]=7;
        }
        return genes;
    }


    @Override
    public void run() {
        // at the beginning map is being created
        Platform.runLater(this::updateSimulationObserver);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(exit){
            if(!pause) {
                day += 1;
                map.getRidOfBodies(this);
                for (Animal animal : animalList) {
                    animal.move();
                }
                map.eatGrass();
                map.createNewAnimals(this);
                map.addGrassToSavana();
                map.addGrassToJungle();
                Platform.runLater(this::updateSimulationObserver);

                // magic happens (5 new animals will appear on the map if engine type is magic)
                if(animalList.size()==5){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    magicHappens();
                    Platform.runLater(this::updateSimulationObserver);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(animalList.size()==0){
                exit = false;
                simulationObserver.endOfSimulation(map);
            }
        }
    }

    // informing observer about changes
    private void updateSimulationObserver(){
        map.getStatisticsData().updateDataList();
        simulationObserver.createMap((AbstractWorldMap) map,gridPane);
        int [] dominant = new int[32];
        dominant = ((AbstractWorldMap) map).getGenotypeDominant();
        simulationObserver.updateSeries(map.getStatisticsData(),dominant,map,day);
        if(chosenAnimal != null){
            simulationObserver.updateChosenAnimalInformation(chosenAnimal);
        }
    }


    public void stop() {
        pause = true;
    }

    public void restart() {
        pause = false;
    }

}



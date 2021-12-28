package agh.ics.oop;
import agh.ics.oop.gui.App;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngineMagic extends AbstractSimulationEngine {
    private int counter = 0;
    private final List<Animal> temporary = new ArrayList<>();
    public SimulationEngineMagic(IWorldMap map, int quantity, App observer, int startEnergy, int moveEnergy, int plantEnergy, GridPane gridPane) {
        super(map, quantity, observer, startEnergy, moveEnergy, plantEnergy, gridPane);
    }

    // 5 new animals with copies of the genomes appears on the map
    @Override
    void magicHappens() {
        if(counter<4){
            counter += 1;
            for(Animal animal: animalList){
                int x = random.nextInt(map.getWidth());
                int y = random.nextInt(map.getHeight());
                while(map.isOccupied(new Vector2d(x,y))){
                    x = random.nextInt(map.getWidth());
                    y = random.nextInt(map.getHeight());
                }
                Animal newAnimal = new Animal(map,new Vector2d(x,y),startEnergy,startEnergy,moveEnergy,plantEnergy,animal.getGenotype());
                map.place(newAnimal);
                temporary.add(newAnimal);
                simulationObserver.updateMagic(map,counter);
            }
            animalList.addAll(temporary);
            temporary.clear();
        }
    }


}

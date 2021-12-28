package agh.ics.oop.Simulation;
import agh.ics.oop.*;
import agh.ics.oop.gui.App;
import javafx.scene.layout.GridPane;

public class SimulationEngine extends  AbstractSimulationEngine{

    public SimulationEngine(IWorldMap map, int quantity, App observer, int startEnergy, int moveEnergy, int plantEnergy, GridPane gridPane) {
        super(map, quantity, observer, startEnergy, moveEnergy, plantEnergy, gridPane);
    }

    @Override
    void magicHappens(){}
}

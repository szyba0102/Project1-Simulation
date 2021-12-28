package agh.ics.oop;

import agh.ics.oop.gui.DataFile;

// one of the maps -> when animal get to the edge it appears on the opposite side of the map

public class Savanna extends AbstractWorldMap{

    public Savanna(int width, int height, double jungleRatio, int startEnergy, int moveEnergy, int plantEnergy) {
        super(width, height, jungleRatio, startEnergy, moveEnergy, plantEnergy);
    }

    public String getName(){
        return "Savanna";
    }

    // method creates new csv file for this map
    public void createDataFile(String fileName,String labels){
        dataFile = new DataFile(fileName+getName(),labels);
    }

    // method canMoveTo is always true because animal can go everywhere
    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
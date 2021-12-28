package agh.ics.oop;

import agh.ics.oop.gui.DataFile;
// one of the maps -> when animal get to the edge it loses round
public class RectangularMap extends AbstractWorldMap{

    public RectangularMap(int width, int height, double jungleRatio, int startEnergy, int moveEnergy, int plantEnergy) {
        super(width, height, jungleRatio, startEnergy, moveEnergy, plantEnergy);
    }

    public String getName() {
        return "Rectangular";
    }

    // method creates new csv file for this map
    public void createDataFile(String fileName,String labels){
        dataFile = new DataFile(fileName+getName(),labels);
    }

    // this method checks if animal can move to chosen location (if it's not the edge)
    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getX() < super.width && position.getX() >= 0 && position.getY() < super.height && position.getY() >= 0;
    }




}

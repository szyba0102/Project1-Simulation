package agh.ics.oop;

import agh.ics.oop.gui.DataFile;

import java.util.List;

public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     */

    void place(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position.
     *
     * @param position
     *            The position of the object.
     * @return Object or null if the position is not occupied.
     */
    Object objectAt(Vector2d position);

    DataFile getDataFile();
    int getWidth();
    void createDataFile(String fileName,String labales);
    int getHeight();
    void addAnimalToKill(Animal animal);
    void eatGrass();
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
    void addGrassToJungle();
    void addGrassToSavana();
    List<Animal> searchingForAnimalsWithDominantGenome();
    void createNewAnimals(AbstractSimulationEngine engine);
    StatisticsData getStatisticsData();
    String getName();
    void getRidOfBodies(AbstractSimulationEngine engine);
}
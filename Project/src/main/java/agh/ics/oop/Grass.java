package agh.ics.oop;

// object grass its eaten by animals on the map, giving them energy

public class Grass implements IMapElement{
    private final Vector2d position;
    private boolean inJungle = false; // it specifies if grass is located in jungle or savanna

    public  Grass(Vector2d position){
        this.position = position;
    }

    public Vector2d getLocation(){
        return this.position;
    }

    public void setInJungle(boolean inJungle) {
        this.inJungle = inJungle;
    }

    // this methods returns file path to the picture that represents this object
    @Override
    public String getNameOfPicture() {
        if(inJungle){
            return "src/main/resources/jungle.png";
        }
        else{
            return "src/main/resources/savana.png";
        }

    }

}
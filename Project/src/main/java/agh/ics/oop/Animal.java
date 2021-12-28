package agh.ics.oop;
import java.util.Random;


public class Animal implements IMapElement{

    private MapDirection orientation;
    private Vector2d location;
    private final IWorldMap map;
    private final int[] genes;
    private final int moveEnergy;
    private final int startEnergy;
    private double actualEnergy;
    private boolean isAlive = true; // this variable specifies if animal should be deleted from map (if it is dead)
    private final int plantEnergy;
    private int lifespan = 0;
    private int quantityOfChildren = 0;
    private int quantityOfChildrenAfterChoice = 0; // this variable count children after animal was chosen by user to being followed
    private final AnimalDescendantCounter animalDescendantCounter = new AnimalDescendantCounter(this); // this object helps to count descendants
    private Animal ancestor = this; // it specifies if animal should inform his ancestor about new children (if is set on this it doesn't have to)
    private boolean chosen = false; // it specifies if this animal is chosen to being followed
    private final Random random = new Random();
    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy, double actualEnergy, int moveEnergy, int plantEnergy, int[] genes){
        Random rand = new Random();
        int x = rand.nextInt(8);
        this.orientation = witchDirection(x);
        this.location = initialPosition;
        this.map = map;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.actualEnergy = actualEnergy;
        this.plantEnergy = plantEnergy;
        this.genes = genes;

    }

    public void setChosen(){
        this.chosen = true;
    }

    //  when different animal was being chosen method setUnchosen clears all extra information
    public void setUnchosen(){
        this.chosen = false;
        animalDescendantCounter.clear();
        quantityOfChildrenAfterChoice = 0;
    }

    // this method changes integer, representing animals move to enum type
    public MapDirection witchDirection(int x){
        return switch (x) {
            case 3-> MapDirection.EAST_SOUTH;
            case 7 -> MapDirection.WEST_NORTH;
            case 5 -> MapDirection.SOUTH_WEST;
            case 1 -> MapDirection.NORTH_EAST;
            case 2 -> MapDirection.EAST;
            case 4 -> MapDirection.SOUTH;
            case 6 -> MapDirection.WEST;
            case 0 -> MapDirection.NORTH;

            default -> throw new IllegalStateException("Unexpected value: " + x);
        };
    }

    //  this method change animal features after giving birth to new animal
    public void afterBaby(Animal kid){
        actualEnergy -= (actualEnergy/4);
        quantityOfChildren += 1;
        // if animal is chosen to being followed it updates needed information
        if(chosen){
            quantityOfChildrenAfterChoice += 1;
            animalDescendantCounter.addDescendant(kid);
            kid.setAncestor(this);
        }
        // if animals ancestor is set to different animal it informs ancestor about new baby and sets baby ancestor to his ancestor
        if(!ancestor.equals(this)){
            System.out.println("ok");
            ancestor.getAnimalDescendantCounter().addDescendant(kid);
            kid.setAncestor(ancestor);
        }
    };
    // it informs other object if this animal is able to have a child
    public boolean canHaveBaby(){
        return this.actualEnergy >= (double) startEnergy / 2;
    }

    // this method update animals energy after eating grass
    public void eat(int fraction){
        actualEnergy += (double) plantEnergy/fraction;
        if(!isAlive && actualEnergy>= moveEnergy){
            isAlive = true;
        }
    }
    // this method change animals location
    // the moves are taken from its genome randomly

    public void move(){
        Vector2d vec = this.location;
        int x = random.nextInt(32); // it choses gen (index) from genome
        int k = genes[x];
        lifespan += 1;
        actualEnergy -= moveEnergy;
        switch (k) {
            case 0 -> {
                if(map.canMoveTo(vec.add(this.orientation.toUnitVector()))){
                    this.location = this.location.add(this.orientation.toUnitVector());
                    ifOutsideTheMap();
                }
            }
            case 4 -> {
                if(map.canMoveTo(vec.subtract(this.orientation.toUnitVector()))){
                    this.location = vec.subtract(this.orientation.toUnitVector());
                    ifOutsideTheMap();
                }
            }
            case 1 -> this.orientation = this.orientation.one();
            case 2 -> this.orientation = this.orientation.two();
            case 3 -> this.orientation = this.orientation.three();
            case 5 -> this.orientation = this.orientation.five();
            case 6 -> this.orientation = this.orientation.six();
            case 7 -> this.orientation = this.orientation.seven();
        }
        positionChanged(vec,this.location); // it informs observers about the change of location
        // if animal don't have enough energy to move anymore it informs map that it should be removed
        if(actualEnergy<moveEnergy){
            isAlive = false;
            toKill();
        }
    }

    // this method make animal appears on the other side of map if animal crossed the boundaries of map
    private void ifOutsideTheMap(){
        int x = location.getX();
        int y = location.getY();
        if(x < 0 && y < 0){
            this.location = new Vector2d(map.getWidth()-1,map.getHeight()-1);
        }
        else if(x < 0 && y > map.getHeight()-1){
            this.location = new Vector2d(map.getWidth()-1,0);
        }
        else if(x> map.getWidth()-1 && y > map.getHeight()-1){
            this.location = new Vector2d(0,0);
        }
        else if(x> map.getWidth()-1 && y < 0){
            this.location = new Vector2d(0,map.getHeight()-1);
        }
        if (x >= map.getWidth()) {
            this.location = new Vector2d(0, y);
        } else if (x < 0) {
            this.location = new Vector2d(map.getWidth() - 1, y);
        } else if (y < 0) {
            this.location = new Vector2d(x, map.getHeight() - 1);
        } else if(y >= map.getHeight()){
            this.location = new Vector2d(x, 0);
        }
    }

    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        map.positionChanged(oldPosition,newPosition,this);
    }

    public void toKill(){
        map.addAnimalToKill(this);
    }

    // this methods enable to get data from this object
    public void setAncestor(Animal animal){
        ancestor = animal;
    }
    public AnimalDescendantCounter getAnimalDescendantCounter(){return animalDescendantCounter;}
    public int getGeneFromGenotype(int index){
        return genes[index];
    }
    public int[] getGenotype(){return genes;}
    public int getLifespan(){return lifespan; }
    public IWorldMap getMap(){return map;}
    public boolean getIsAlive(){return this.isAlive;}
    public int getQuantityOfChildren(){
        return quantityOfChildren;
    }
    public int getQuantityOfChildrenAfterChoice(){
        return quantityOfChildrenAfterChoice;
    }
    public double getEnergy(){
        return actualEnergy;
    }
    public Vector2d getLocation(){return new Vector2d(this.location.getX(), this.location.getY());}

    // this methods returns file path to the picture that represents this object
    @Override
    public String getNameOfPicture() {
        if(actualEnergy>=100){
            return "src/main/resources/10.png";
        }
        int firstDigit = (int) (actualEnergy/10);
        return "src/main/resources/"  + String.valueOf(firstDigit)+ ".png";
    }

}

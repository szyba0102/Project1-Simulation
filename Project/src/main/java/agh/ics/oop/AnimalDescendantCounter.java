package agh.ics.oop;
import java.util.ArrayList;
import java.util.List;

// this object assigned to specific animal
// it is used to count animal descendents, by adding them to the list

public class AnimalDescendantCounter {
    Animal parent;
    List<Animal> descendant = new ArrayList<>();
    public AnimalDescendantCounter(Animal animal){
        this.parent = animal;
    }

    // descendents now that they are supposed to inform this object about new children, when their variable named ancestor is set for different animal (not them)
    public void addDescendant(Animal animal){
        descendant.add(animal);
    }

    public int quantityOfDescendents(){return  descendant.size();}

    // when animal isn't longer obligated to counting his descendents, this method informs all of the descendents that they should stop counting theirs descendents too
    // after informing all descendents , list is being cleared
    public void clear(){
        for(Animal animal:descendant){
            animal.setAncestor(animal);
        }
        descendant.clear();
    }

}


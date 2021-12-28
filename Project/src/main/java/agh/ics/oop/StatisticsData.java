package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

// class which updates statistical data for specific map and stores them until they are saved by user
// it is used to update charts

public class StatisticsData {
    private double quantityOfLivingAnimals = 0;
    private int quantityOfGrass = 0;
    private double totalEnergy = 0;
    private double quantityOfDeadAnimals = 0;
    private int totalLifespan = 0;
    private int quantityOfChildrenOfLivingAnimals = 0;
    private final List<String> dataList = new ArrayList<>();

    public StatisticsData(){

    }

    // this method is called when user decides to save statistical data
    // saved data is moved to cvs file and  deleted from this object
    // double values are set to have two decimal places

    public void updateDataList(){
        String averageOfEnergy;
        String averageOfChildren;
        if(quantityOfLivingAnimals != 0){
            averageOfEnergy = String.format("%.2f",totalEnergy/quantityOfLivingAnimals);
            averageOfChildren = String.format("%.2f",quantityOfChildrenOfLivingAnimals / quantityOfLivingAnimals);
        }
        else{
            averageOfEnergy ="0";
            averageOfChildren ="0";
        }
        String averageLifespan;
        if(quantityOfDeadAnimals!=0){
            averageLifespan = String.format("%.2f",totalLifespan/quantityOfDeadAnimals);
        }
        else {
            averageLifespan ="0";}
        String newVerse =quantityOfLivingAnimals +";"+quantityOfGrass+";"+ averageOfEnergy +";"+ averageLifespan +";"+ averageOfChildren;
        dataList.add(newVerse);
    }

    // this methods enable to update data
    public void clearList(){
        dataList.clear();
    }

    public void updateQuantityOfLivingAnimals(int number){
        quantityOfLivingAnimals += number;
    }

    public void updateQuantityOfGrass(int number){
        quantityOfGrass += number;
    }

    public void updateTotalEnergy(double number){
        totalEnergy += number;
    }

    public void updateQuantityOfDeadAnimals(int number){
        quantityOfDeadAnimals += number;
    }

    public void updateTotalLifespan(int number){
        totalLifespan += number;
    }

    public void updateQuantityOfChildrenOfLivingAnimals(int number){
        quantityOfChildrenOfLivingAnimals += number;
    }


    // this methods enable other object to get data
    public List<String> getDataList(){
        return dataList;
    }

    public int getQuantityOfLivingAnimals(){
        return (int) quantityOfLivingAnimals;
    }

    public int getQuantityOfGrass(){
        return quantityOfGrass;
    }

    public double getAverageLifespan(){
        if(quantityOfDeadAnimals == 0){return 0;}
        else{
            return totalLifespan/quantityOfDeadAnimals;}
    }
    public double getAverageOfEnergy(){
        if(quantityOfLivingAnimals == 0){return 0;}
        return totalEnergy/quantityOfLivingAnimals;
    }
    public double getAverageOfChildren(){
        if(quantityOfLivingAnimals == 0){return 0;}
        return quantityOfChildrenOfLivingAnimals / quantityOfLivingAnimals;

    }

}

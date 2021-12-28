package agh.ics.oop.gui;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
// object that creates and updates csv files
public class DataFile {
    File newFile;
    private final List<Double> quantityOfAnimals  = new ArrayList<>();
    private final List<Integer> quantityOfGrass  = new ArrayList<>();
    private final List<Double> AvgEnergy  = new ArrayList<>();
    private final List<Double> AvgLifespan  = new ArrayList<>();
    private final List<Double> AvgQuantityOfChildren  = new ArrayList<>();

    public DataFile(String fileName, String labels){
        newFile = new File("statistics/" + fileName + ".csv");
        addToFile(labels);
    }

    // adds new line to cvs file
    public void addToFile(String data){
        try {
            FileWriter outputfile = new FileWriter(newFile,true);
            BufferedWriter bw = new BufferedWriter(outputfile);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(data);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // updates new data
    public void addNewData(List<String> data){
        for(String verse: data ){
            verse = verse.replace('.',',');
            addToFile(verse);
            updateLists(verse);
        }
    }
    // update lists that store data
    private void updateLists(String data){
        String[] newData = data.split(";");
        for(int i=0; i< newData.length;i++){
            newData[i] = newData[i].replace(',','.');
        }

        quantityOfAnimals.add(Double.parseDouble(newData[0]));
        quantityOfGrass.add(Integer.parseInt(newData[1]));
        AvgEnergy.add(Double.parseDouble(newData[2]));
        AvgLifespan.add(Double.parseDouble(newData[3]));
        AvgQuantityOfChildren.add(Double.parseDouble(newData[4]));

    }

    // method calculate average values
    public void endOfSimulation(){
        String averagedValues;
        double averagedQuantityOfAnimals = 0;
        for(double val: quantityOfAnimals){
            averagedQuantityOfAnimals += val;
        }
        System.out.println(quantityOfAnimals.size());
        averagedQuantityOfAnimals /= quantityOfAnimals.size();

        double averagedQuantityOfGrass = 0;
        for(int val: quantityOfGrass){
            averagedQuantityOfGrass += val;
        }
        System.out.println(quantityOfGrass.size());
        averagedQuantityOfGrass /= quantityOfGrass.size();

        double averagedAvgEnergy = 0;
        for(double val: AvgEnergy){
            averagedAvgEnergy += val;
        }
        System.out.println(AvgEnergy.size());
        averagedAvgEnergy /= AvgEnergy.size();

        double averagedAvgLifespan = 0;
        for(double val: AvgLifespan){
            averagedAvgLifespan += val;
        }
        System.out.println(AvgLifespan.size());
        averagedAvgLifespan /= AvgLifespan.size();

        double averagedAvgQuantityOfChildren = 0;
        for(double val: AvgQuantityOfChildren){
            averagedAvgQuantityOfChildren += val;
        }
        System.out.println(AvgQuantityOfChildren.size());
        averagedAvgQuantityOfChildren /= AvgQuantityOfChildren.size();


        String avg = "avg;avg;avg;avg;avg";
        averagedValues = averagedQuantityOfAnimals + ";" + averagedQuantityOfGrass + ";" + averagedAvgEnergy + ";" + averagedAvgLifespan+ ";" + averagedAvgQuantityOfChildren;
        averagedValues = averagedValues.replace('.',',');
        addToFile(avg);
        System.out.println(averagedValues);
        addToFile(averagedValues);
    }

}


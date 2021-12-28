package agh.ics.oop.gui;

import agh.ics.oop.IWorldMap;
import agh.ics.oop.StatisticsData;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartForMaps {
    private final IWorldMap map;
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private final XYChart.Series<Number, Number> seriesLivingAnimals = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> seriesGrass = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> seriesAverageEnergy = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> seriesAverageLifespan = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> seriesAverageChildren = new XYChart.Series<>();


    public ChartForMaps(IWorldMap map){
        this.map = map;
    }

    public javafx.scene.chart.Chart createChart(){
        xAxis.setLabel("Era");
        xAxis.setAnimated(false); // axis animations are removed
        yAxis.setLabel("Value");
        yAxis.setAnimated(false); // axis animations are removed

        lineChart.setTitle("Simulation " + map.getName() + " Data Chart");
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(false);

        xAxis.setForceZeroInRange(false);

        seriesLivingAnimals.setName("Quantity of living animals");
        seriesGrass.setName("Quantity of grass");
        seriesAverageEnergy.setName("Average energy");
        seriesAverageLifespan.setName("Average lifespan");
        seriesAverageChildren.setName("Average children quantity");

        lineChart.getData().add(seriesLivingAnimals);
        lineChart.getData().add(seriesGrass);
        lineChart.getData().add(seriesAverageEnergy);
        lineChart.getData().add(seriesAverageLifespan);
        lineChart.getData().add(seriesAverageChildren);

        return lineChart;
    }
    // updating charts data
    public void updateSeries(StatisticsData data, int day){
        int livingAnimals = data.getQuantityOfLivingAnimals();
        int grass = data.getQuantityOfGrass();
        double averageEnergy = data.getAverageOfEnergy();
        double averageLifespan = data.getAverageLifespan();
        double averageChildren = data.getAverageOfChildren();
        seriesLivingAnimals.getData().add(new XYChart.Data<>(day, livingAnimals));
        seriesGrass.getData().add(new XYChart.Data<>(day, grass));
        seriesAverageEnergy.getData().add(new XYChart.Data<>(day, averageEnergy));
        seriesAverageLifespan.getData().add(new XYChart.Data<>(day, averageLifespan));
        seriesAverageChildren.getData().add(new XYChart.Data<>(day, averageChildren));
        if (seriesLivingAnimals.getData().size() > 10) {
            seriesLivingAnimals.getData().remove(0);
            seriesGrass.getData().remove(0);
            seriesAverageEnergy.getData().remove(0);
            seriesAverageLifespan.getData().remove(0);
            seriesAverageChildren.getData().remove(0);
        }

    }}

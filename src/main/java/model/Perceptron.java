package main.java.model;

import main.java.service.DataReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Perceptron {

    private List<DataModel> trainSet;
    private List<DataModel> testSet;
    private List<Double> weights;
    private double theta;
    private double alpha;
    private int dimension;

    public Perceptron() {

    }

    private int isActivated(double net) {
        return net >= theta ? 1 : 0;
    }

    public int classify(List<Double> inputs) {
        double net = 0;
        for (int i = 0; i < weights.size(); i++)
            net += inputs.get(i) * weights.get(i);

        return isActivated(net);
    }

    public void train(List<Double> inputs, int expected) {
        int classified = classify(inputs);
        int error = expected - classified;

        if (error != 0) {
            List<Double> newWeights = new ArrayList<>();
            for (int i = 0; i < weights.size(); i++)
                newWeights.add(weights.get(i) + error * inputs.get(i) * alpha);

            theta += error * alpha * (-1);
            weights = newWeights;
        }
    }

    public void generateTheta(double min, double max) {
        theta = min + Math.random() * (max - min);
    }

    public void generateWeights(double min, double max) {
        weights = new ArrayList<>();
        for (int i = 0; i < dimension; i++)
            weights.add(min + Math.random() * (max - min));
    }

    public static void setLabelsToDataModels(List<DataModel> dataModels) {
        ArrayList<Map.Entry<String, List<DataModel>>> entries = groupDataModels(dataModels);
        entries.get(0).getValue().forEach(model -> model.setLabel(0));
        entries.get(1).getValue().forEach(model -> model.setLabel(1));
    }

    public static ArrayList<Map.Entry<String, List<DataModel>>> groupDataModels(List<DataModel> dataModels) {
        return new ArrayList<>(dataModels.stream()
                .collect(Collectors.groupingBy(DataModel::getName))
                .entrySet());
    }

    public static List<DataModel> initSet(List<String> lines) {
        List<DataModel> dataModels = lines.stream().map(line -> {
            List<Double> vector = DataReader.getVectorFromLine(line);
            String name = DataReader.getNameFromLine(line);
            return new DataModel(vector, name);
        }).collect(Collectors.toList());

        setLabelsToDataModels(dataModels);

        return dataModels;
    }

    public int getTestSetLabelSize(int label) {
        return groupDataModels(testSet).get(label).getValue().size();
    }

    public String getTestLabelName(int label) {
        return groupDataModels(testSet).get(label).getKey();
    }

    public List<DataModel> getTrainSet() {
        return trainSet;
    }

    public void setTrainSet(List<DataModel> trainSet) {
        this.trainSet = trainSet;
    }

    public List<DataModel> getTestSet() {
        return testSet;
    }

    public void setTestSet(List<DataModel> testSet) {
        this.testSet = testSet;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getTheta() {
        return theta;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}
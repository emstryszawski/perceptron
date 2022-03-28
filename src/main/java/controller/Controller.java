package main.java.controller;

import main.java.model.DataModel;
import main.java.model.Perceptron;
import main.java.service.DataReader;
import main.java.view.PerceptronGUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

public class Controller {

    private final PerceptronGUI view;
    private final Perceptron model;

    public Controller(PerceptronGUI view, Perceptron model) {
        this.view = view;
        this.model = model;

        this.view.addAlphaListener(new AlphaListener());
        this.view.addTrainListener(new TrainListener());
        this.view.addTestListener(new TestListener());
        this.view.addClassifyButtonListener(new ClassifyListener());
    }

    private class AlphaListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            JSlider source = (JSlider) changeEvent.getSource();
            double alpha = (double) source.getValue() / 10;
            model.setAlpha(alpha);
        }
    }

    private class TrainListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            List<DataModel> trainSet = readFileAndInitSet();
            Collections.shuffle(trainSet);
            model.setTrainSet(trainSet);
            model.setDimension(model.getTrainSet().get(0).getVector().size());
            model.generateWeights(-5, 5);
            model.generateTheta(-5, 5);

            for (DataModel dataModel : model.getTrainSet()) {
                List<Double> inputs = dataModel.getVector();
                int label = dataModel.getLabel();
                model.train(inputs, label);
            }
            view.showTrainSetOnTextArea(model.getTrainSet());
            view.addInfo(model.getWeights(), model.getTheta());
        }
    }

    private class TestListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            List<DataModel> testSet = readFileAndInitSet();
            Collections.shuffle(testSet);
            model.setTestSet(testSet);
            int count = 0, count0 = 0, count1 = 0;
            view.printOnTextArea2("");
            StringBuilder builder = new StringBuilder();
            for (DataModel dataModel : model.getTestSet()) {
                List<Double> inputs = dataModel.getVector();
                int label = dataModel.getLabel();
                int guess = model.classify(inputs);
                if (label == guess) {
                    count++;
                    if (guess == 0) count0++;
                    else count1++;
                }

                String guessedName = model.getTestLabelName(guess);
                builder.append(dataModel.getVector())
                        .append(";").append(dataModel.getName())
                        .append(" ; ").append(guessedName).append("\n");
            }
            view.printOnTextArea2(builder.toString());
            int size = model.getTestSet().size();
            view.showAccuracyOnTextField(view.getTextField(1), getAccuracy(count, size) + "%");
            size = model.getTestSetLabelSize(0);
            view.showAccuracyOnTextField(view.getTextField(2), getAccuracy(count0, size) + "%");
            size = model.getTestSetLabelSize(1);
            view.showAccuracyOnTextField(view.getTextField(3), getAccuracy(count1, size) + "%");
        }
    }

    private int getAccuracy(int a, int b) {
        return (int) (((double) a / b) * 100);
    }

    private List<DataModel> readFileAndInitSet() {
        List<DataModel> dataModels = null;
        try {
            File file = view.openInputDialog();
            if (file == null) {
                throw new FileNotFoundException();
            }
            List<String> lines = DataReader.getLinesFromFile(file);
            dataModels = Perceptron.initSet(lines);
        } catch (FileNotFoundException e) {
            view.reportInputError();
        }
        return dataModels;
    }

    private class ClassifyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            List<Double> vectorFromInput = view.getVectorFromInput();
            int guess = model.classify(vectorFromInput);
            view.showClassificationResult(vectorFromInput, model.getTestLabelName(guess));
        }
    }
}

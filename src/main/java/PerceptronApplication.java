package main.java;

import main.java.controller.Controller;
import main.java.model.Perceptron;
import main.java.view.PerceptronGUI;

import javax.swing.*;

public class PerceptronApplication {

    public PerceptronApplication() {
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    public static void main(String[] args) {
        new PerceptronApplication();
    }

    private void createAndShowGUI() {
        PerceptronGUI view = new PerceptronGUI();
        Perceptron model = new Perceptron();
        Controller controller = new Controller(view, model);
    }
}

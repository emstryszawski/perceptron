package main.java.view;

import main.java.model.DataModel;
import main.java.service.DataReader;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Hashtable;
import java.util.List;

public class PerceptronGUI {
    private JPanel mainPanel;
    private JSlider alphaSlider;
    private JButton trainSetButton;
    private JButton testSetButton;
    private JButton classifyButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JLabel alphaLabel;
    private JLabel infoLabel;

    public PerceptronGUI() {
        JFrame frame = new JFrame("Perceptron");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // learningRateLabel Config
        ImageIcon icon = new ImageIcon("src/resources/icons/alpha.png");
        alphaLabel = new JLabel("Stała uczenia");
        alphaLabel.setIcon(icon);

        // learningRateSlider Config
        alphaSlider = new JSlider(0, 10, 1);
        alphaSlider.setMajorTickSpacing(1);
        alphaSlider.setPaintTicks(true);
        Hashtable<Integer,JLabel> labelTable = new java.util.Hashtable<>();

        for (double i = 10; i >= 0; i--)
            labelTable.put((int) i, new JLabel((i / 10) + ""));

        alphaSlider.setLabelTable( labelTable );

        // trainSetButton
        trainSetButton = new JButton();
        trainSetButton.setIcon(new ImageIcon("src/resources/icons/trainset.png"));

        // testSetButton
        testSetButton = new JButton();
        testSetButton.setIcon(new ImageIcon("src/resources/icons/testset.png"));

        // classifyButton
        classifyButton = new JButton();
        classifyButton.setIcon(new ImageIcon("src/resources/icons/classify.png"));
    }

    public void addTrainListener(ActionListener actionListener) {
        trainSetButton.addActionListener(actionListener);
    }

    public File openInputDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Pliki z rozszerzeniem .csv","csv"));
        fileChooser.setCurrentDirectory(new File("src/resources/csv"));
        int status = fileChooser.showOpenDialog(trainSetButton);
        if (status == JFileChooser.APPROVE_OPTION)
            return fileChooser.getSelectedFile();
        return null;
    }

    public void reportInputError() {
        JOptionPane.showMessageDialog(trainSetButton, "Wybierz odpowiedni plik");
    }

    public void showTrainSetOnTextArea(List<DataModel> trainSet) {
        textArea1.setText("");
        trainSet.forEach(model -> textArea1.append(model + "\n"));
    }

    public void showTestSetOnTextArea(List<DataModel> testSet) {
        textArea2.setText("");
        testSet.forEach(model -> textArea2.append(model + "\n"));
    }

    public void addTestListener(ActionListener testListener) {
        testSetButton.addActionListener(testListener);
    }

    public void addAlphaListener(ChangeListener alphaListener) {
        alphaSlider.addChangeListener(alphaListener);
    }

    public void printOnTextArea2(String s) {
        textArea2.setText(s);
    }

    public void showAccuracyOnTextField(JTextField textField, String s) {
        textField.setText(s);
    }

    public void addClassifyButtonListener(ActionListener listener) {
        classifyButton.addActionListener(listener);
    }

    public List<Double> getVectorFromInput() {
        String input = JOptionPane.showInputDialog(classifyButton, "Wprowadź wektor (oddziel składowe przecinkiem)");
        return DataReader.getVectorFromLine(input);
    }

    public void showClassificationResult(List<Double> vectorFromInput, String name) {
        JOptionPane.showMessageDialog(classifyButton, vectorFromInput + " -> " + name);
    }

    public JTextField getTextField(int i) {
        switch (i) {
            case 1: return textField1;
            case 2: return textField2;
            case 3: return textField3;
        }
        return null;
    }

    public void addInfo(List<Double> weights, double theta) {
        infoLabel.setText("Wagi:" + weights + "    " +
                "Theta: " + theta);
    }
}
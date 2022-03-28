package main.java.model;

import java.util.List;
import java.util.Objects;

public class DataModel {

    private final List<Double> vector;
    private final String name;
    private int label;

    public DataModel(List<Double> vector, String name) {
        this.vector = vector;
        this.name = name;
    }

    @Override
    public String toString() {
        return vector + ";" + name + ";" + label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModel dataModel = (DataModel) o;
        return vector.equals(dataModel.vector) && name.equals(dataModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector, name);
    }

    public List<Double> getVector() {
        return vector;
    }

    public String getName() {
        return name;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
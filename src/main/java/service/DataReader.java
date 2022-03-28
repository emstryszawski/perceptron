package main.java.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {

    private DataReader() {

    }

    public static List<String> getLinesFromFile(File file) throws FileNotFoundException {
        if (!file.exists())
            throw new FileNotFoundException();

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String getNameFromLine(String string) {
        return Arrays.stream(string.split(";"))
                .filter(s -> s.matches("\\D+"))
                .map(String::toUpperCase)
                .collect(Collectors.joining());
    }

    public static List<Double> getVectorFromLine(String string) {
        return Arrays.stream(string.split(";"))
                .filter(s -> s.matches("[+-]?([0-9]*[.])?[0-9]+"))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }
}

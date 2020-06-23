package com.wiki.builder;

import com.wiki.model.WikiData;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class WikiDataBuilder implements ModelBuilderI<List<WikiData>> {
    @Override
    public List<WikiData> build() {

        ArrayList<WikiData> dataSets = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty("user.dir")+"/"+"input"))) {

            paths.filter(Files::isRegularFile).forEach(file -> {

                List<String> lines = Collections.emptyList();
                try {
                    lines =
                            Files.readAllLines(file, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                assert lines != null : "Empty file: " + file.getFileName();
                try {
                    WikiData dataSet = new WikiData();
                    dataSet.setArticle(lines.get(0));
                    dataSet.setQuestions(lines.subList(1, 6));
                    dataSet.setProvidedAnswers(Arrays.asList(lines.get(6).split("[;]")));
                    dataSet.setFileName(file.getFileName().toString());
                    dataSets.add(dataSet);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("input file invalid format  : " + e);
                }


            });

        } catch (IOException e) {
            System.err.println("OOPS! can't read input file, exception : " + e);
        }
        return dataSets;

    }
}

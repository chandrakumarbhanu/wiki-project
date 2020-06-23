package com.wiki.matcher;

import java.util.*;
import java.util.stream.Stream;


public class ArticleSentenceMatcher {
    //question words shud be matching in the sentence
    //if fully matching returns 2 sentence then rank it and return the best scorer.
    public String matchArticleSentence(List<String> articleSentence,List<String> questionWords){
        Map<String, Integer> rankMapperForLines = new HashMap<>();

        for(String line : articleSentence) {

            Set<String> inputStringList = new HashSet(Arrays.asList(line.trim().split(" ")));


            if(questionWords.stream().allMatch(inputStringList::contains))
            {
                return line;
            }
            Boolean match = false;
            int noOfTimesMatch = 0;
            for(String q:questionWords ){
                match = false;

                for (String input : inputStringList) {
                    if (q.contains(input) || input.contains(q)) {
                        match = true;
                        ++noOfTimesMatch;
                        break;

                    }
                }
                //penalty
                if(!match){
                    --noOfTimesMatch;
                }

            }
           if(match && (noOfTimesMatch == questionWords.size())){
                return line;
            }
            rankMapperForLines.put(line, noOfTimesMatch);

        }
        Stream<Map.Entry<String,Integer>> sorted =
                rankMapperForLines.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));

        //no match
        return sorted.findFirst().get().getKey();

    }

    public int matchArticleSentenceWithAnswers(List<String> articleSentence,List<String> answersWords){
        int counter = -1;
        Map<Integer, Integer> rankMapperForLines = new HashMap<>();


        for(String line : articleSentence) {
            ++counter;

            Set<String> inputStringList = new HashSet(Arrays.asList(line.trim().split(" ")));

            //answer line words if fully contained in probale article sentence.

            if(inputStringList.stream().allMatch(answersWords::contains))
            {
                return counter;
            }
            Boolean match = false;

            int noOfTimesMatch = 0;

            for(String q:inputStringList ){
                match = false;
                for (String input : answersWords) {
                    if (q.contains(input) || input.contains(q)) {
                        match = true;
                        ++noOfTimesMatch;
                        break;

                    }
                }
                //penalty
                if(!match){
                    --noOfTimesMatch;
                }

            }
            if(match && (noOfTimesMatch == answersWords.size())){
                return counter;
            }
            rankMapperForLines.put(counter, noOfTimesMatch);
        }
        Stream<Map.Entry<Integer,Integer>> sorted =
                rankMapperForLines.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        //no match
        return sorted.findFirst().get().getKey();

    }
}

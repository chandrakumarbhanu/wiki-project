package com.wiki.facade;



import com.wiki.matcher.ArticleSentenceMatcher;
import com.wiki.model.WikiData;
import com.wiki.util.NLPPreprocessor;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AnswerMatcherFacade {

    private ArticleSentenceMatcher articleSentenceMatcher = new ArticleSentenceMatcher();



    public void publishAnswers(List<WikiData> wikiDataList){
        for(WikiData wikiData : wikiDataList){

            assert wikiData != null : "Input data can't be null";

            //get the orrdered answer for the paraphrase
            List<String> answers = getAnswerList(wikiData);
            writeToFile(wikiData, answers);


            //return answers;

        }
    }

    private List<String> getAnswerList(WikiData wikiData) {
        List<String> answers = new ArrayList<>();
        //preprocess the article by removing all article and un necessary words like i an , when , where etc.
        String processedArticle = NLPPreprocessor.preProcess(wikiData.getArticle());
        //split the article based on period "."
        List<String> processedArticleSentence = Arrays.asList(processedArticle.split("[.]"));
        List<String> jumbledAnswerList = new ArrayList<>();
        //add all the jubled answer in pre processed format.
        for (String answer : wikiData.getProvidedAnswers()) {
            jumbledAnswerList.add(NLPPreprocessor.preProcess(answer));
        }


        //iterate each question
        //for each question break into text words.make sure all article, supporting words are removed by preprocessing
        //match the question text words to article sentence.
        //for that matched article sentence match to jumbled answer
        //get that jumbled answer index
        //retrieve the answer sentence based on index and add it sequentially.

        wikiData.getQuestions().stream().forEach(question -> {

                    question = NLPPreprocessor.preProcess(question);

                    String matchedArticleSentence = articleSentenceMatcher.matchArticleSentence(processedArticleSentence, Arrays.asList(question.split(" ")));

                    int answerIndex = articleSentenceMatcher.matchArticleSentenceWithAnswers(jumbledAnswerList, Arrays.asList(matchedArticleSentence.trim().split(" ")));
                    answers.add(wikiData.getProvidedAnswers().get(answerIndex));
                }

        );
        return answers;
    }

    /*

    write to output folder with same file having the correct sequence of answer.
     */
    private void writeToFile(WikiData wikiData, List<String> answers) {
        //write to output folder with new file
        FileWriter writer = null;
        try {
            String baseDir = System.getProperty("user.dir") + "/" + "output";
            File mkDir = new File(baseDir);
            mkDir.mkdirs();
            File file = new File( baseDir+ "/" + wikiData.getFileName());

            // creates the file
            file.createNewFile();

            // creates a FileWriter Object
            writer = new FileWriter(file);

            // Writes the content to the file
            for(String answer: answers) {
                writer.write(answer+System.lineSeparator());
            }
            writer.flush();
            writer.close();
        }
        catch(Exception e){
            System.err.println("Exception while writing file "+e.getMessage());
        }
    }

    /**public List<String> getAnswers(final WikiData wikiData) {
        assert wikiData != null : "Input data can't be null";

        List<String> answers = getAnswerList(wikiData);

        return answers;
    }**/







}
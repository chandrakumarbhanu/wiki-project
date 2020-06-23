package com.wiki;


import com.wiki.builder.WikiDataBuilder;
import com.wiki.facade.AnswerMatcherFacade;


/**
 *
 */
public class WikiMain {
    public static void main(String[] args){

        WikiDataBuilder modelBuilder = new WikiDataBuilder();
        AnswerMatcherFacade answerMatcherFacade = new AnswerMatcherFacade();

        answerMatcherFacade.publishAnswers(modelBuilder.build());


    }
}

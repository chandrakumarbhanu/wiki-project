package com.wiki.util;


final public class NLPPreprocessor {


    public static String preProcess(final String text) {

        return text
                .replaceAll("(?i)\\b(the|to|and|for|in|a|an|their|is|of|while|much|more|one|there|some|that|do|did|does|what|why|which|who|whom|how|it|are|at|by|,)\\b", "")
                .replaceAll("(\\?|;|,|\\s)+", " ")
                .trim()
                .toLowerCase();
    }

}

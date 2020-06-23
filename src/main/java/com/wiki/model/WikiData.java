package com.wiki.model;

import lombok.Data;

import java.util.List;

@Data
public class WikiData {

    List<String> questions;
    List<String> providedAnswers;
    String article;
    String fileName;

}

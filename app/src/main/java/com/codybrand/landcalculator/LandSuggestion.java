package com.codybrand.landcalculator;

public class LandSuggestion {
    Integer tapped = 0;
    Integer untapped = 0;
    Boolean red = false;
    Boolean blue = false;
    Boolean black = false;
    Boolean white = false;
    Boolean green = false;
    Boolean colorless = false;

    LandSuggestion(){}

    LandSuggestion(Integer tapped, Integer untapped) {
        this.tapped = tapped;
        this.untapped = untapped;
    }
}

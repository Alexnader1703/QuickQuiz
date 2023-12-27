package com.example.quickquiz;

public class Category {
    private String title;
    private int correctAnswersPercentage;

    public Category(String title, int correctAnswersPercentage) {
        this.title = title;
        this.correctAnswersPercentage = correctAnswersPercentage;
    }

    public String getTitle() {
        return title;
    }

    public int getCorrectAnswersPercentage() {
        return correctAnswersPercentage;
    }
}
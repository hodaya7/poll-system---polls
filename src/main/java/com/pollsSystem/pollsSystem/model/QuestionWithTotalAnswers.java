package com.pollsSystem.pollsSystem.model;

public class QuestionWithTotalAnswers {
    private Long id;
    private String title;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;
    private int totalAnswers;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getFirstAnswer() { return firstAnswer; }
    public void setFirstAnswer(String firstAnswer) { this.firstAnswer = firstAnswer; }

    public String getSecondAnswer() { return secondAnswer; }
    public void setSecondAnswer(String secondAnswer) { this.secondAnswer = secondAnswer; }

    public String getThirdAnswer() { return thirdAnswer; }
    public void setThirdAnswer(String thirdAnswer) { this.thirdAnswer = thirdAnswer; }

    public String getFourthAnswer() { return fourthAnswer; }
    public void setFourthAnswer(String fourthAnswer) { this.fourthAnswer = fourthAnswer; }

    public int getTotalAnswers() { return totalAnswers; }
    public void setTotalAnswers(int totalAnswers) { this.totalAnswers = totalAnswers; }
}

package com.pollsSystem.pollsSystem.model;

import java.time.LocalDateTime;

public class PollAnswer {
    private Long answerNumber;
    private Long userId;
    private Long questionId;
    private LocalDateTime dateTime;
    private AnswerOption selectedAnswer;

    public PollAnswer(Long answerNumber, Long userId, Long questionId, LocalDateTime dateTime, AnswerOption selectedAnswer) {
        this.answerNumber = answerNumber;
        this.userId = userId;
        this.questionId = questionId;
        this.dateTime = dateTime;
        this.selectedAnswer = selectedAnswer;
    }

    public Long getAnswerNumber() {
        return answerNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public AnswerOption getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setAnswerNumber(Long answerNumber) {
        this.answerNumber = answerNumber;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setSelectedAnswer(AnswerOption selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}

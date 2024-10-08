package com.pollsSystem.pollsSystem.model;

import java.util.HashMap;
import java.util.Map;

public class QuestionWithAnswers {
        private Long id;
        private String title;
        private String firstAnswer;
        private String secondAnswer;
        private String thirdAnswer;
        private String fourthAnswer;
        private Map<String, Integer> answerCounts = new HashMap<>();

        // Constructors
        public QuestionWithAnswers() {}

        public QuestionWithAnswers(Long id, String title, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer) {
                this.id = id;
                this.title = title;
                this.firstAnswer = firstAnswer;
                this.secondAnswer = secondAnswer;
                this.thirdAnswer = thirdAnswer;
                this.fourthAnswer = fourthAnswer;
        }

        // Getters and Setters
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getFirstAnswer() {
                return firstAnswer;
        }

        public void setFirstAnswer(String firstAnswer) {
                this.firstAnswer = firstAnswer;
        }

        public String getSecondAnswer() {
                return secondAnswer;
        }

        public void setSecondAnswer(String secondAnswer) {
                this.secondAnswer = secondAnswer;
        }

        public String getThirdAnswer() {
                return thirdAnswer;
        }

        public void setThirdAnswer(String thirdAnswer) {
                this.thirdAnswer = thirdAnswer;
        }

        public String getFourthAnswer() {
                return fourthAnswer;
        }

        public void setFourthAnswer(String fourthAnswer) {
                this.fourthAnswer = fourthAnswer;
        }

        public Map<String, Integer> getAnswerCounts() {
                return answerCounts;
        }

        public void setAnswerCounts(Map<String, Integer> answerCounts) {
                this.answerCounts = answerCounts;
        }

        public void addAnswerCount(String answer, Integer count) {
                answerCounts.put(answer, count);
        }

        @Override
        public String toString() {
                return "QuestionWithAnswers{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", firstAnswer='" + firstAnswer + '\'' +
                        ", secondAnswer='" + secondAnswer + '\'' +
                        ", thirdAnswer='" + thirdAnswer + '\'' +
                        ", fourthAnswer='" + fourthAnswer + '\'' +
                        ", answerCounts=" + answerCounts +
                        '}';
        }
}

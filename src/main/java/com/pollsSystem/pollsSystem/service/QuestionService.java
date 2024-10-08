package com.pollsSystem.pollsSystem.service;

import com.pollsSystem.pollsSystem.model.Question;
import com.pollsSystem.pollsSystem.model.QuestionWithAnswerCounts;
import com.pollsSystem.pollsSystem.model.QuestionWithAnswers;
import com.pollsSystem.pollsSystem.model.QuestionWithTotalAnswers;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    void createQuestion(Question question);
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
    void updateQuestionById(Long questionId, Question question);
    void deleteQuestion(Long id) throws Exception;
    //דרישה 1
    QuestionWithAnswers getQuestionWithAnswers(Long questionId);
    //דרישה 2
    public QuestionWithTotalAnswers getQuestionWithTotalAnswers(Long questionId);
    //5
    public List<Question> getAllQuestions2();
    //5
    public Map<Long, Map<String, Integer>> getAnswerCountsByAllQuestions();
}

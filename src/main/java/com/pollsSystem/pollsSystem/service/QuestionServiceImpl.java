package com.pollsSystem.pollsSystem.service;

import com.pollsSystem.pollsSystem.model.Question;
import com.pollsSystem.pollsSystem.model.QuestionWithAnswerCounts;
import com.pollsSystem.pollsSystem.model.QuestionWithAnswers;
import com.pollsSystem.pollsSystem.model.QuestionWithTotalAnswers;
import com.pollsSystem.pollsSystem.repository.PollAnswerRepository;
import com.pollsSystem.pollsSystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Override
    public void createQuestion(Question question) {
        questionRepository.createQuestion(question);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.getQuestionById(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.getAllQuestions();
    }

    @Override
    public void updateQuestionById(Long questionId, Question question) {
        questionRepository.updateQuestionById(questionId, question);
    }

    @Autowired
    PollAnswerService pollAnswerService;
    @Override
    public void deleteQuestion(Long id) throws Exception {
        if (questionRepository.getQuestionById(id) != null){
            // First, delete all answers related to the question
            pollAnswerService.deletePollAnswersByQuestionId(id);
            questionRepository.deleteQuestion(id);}
        else
            throw new Exception("question is not exists");
    }
//דרישה 1
    @Override
    public QuestionWithAnswers getQuestionWithAnswers(Long questionId) {
        return questionRepository.getQuestionWithAnswers(questionId);
    }
    //דרישה 2
    public QuestionWithTotalAnswers getQuestionWithTotalAnswers(Long questionId) {
        return questionRepository.getQuestionWithTotalAnswers(questionId);
    }

    @Override
    public List<Question> getAllQuestions2() {
        return questionRepository.getAllQuestions2();
    }

    @Override
    public Map<Long, Map<String, Integer>> getAnswerCountsByAllQuestions() {
        return questionRepository.getAnswerCountsByAllQuestions();
    }

}

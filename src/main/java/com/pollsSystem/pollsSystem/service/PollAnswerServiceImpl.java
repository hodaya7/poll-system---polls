package com.pollsSystem.pollsSystem.service;

import com.pollsSystem.pollsSystem.model.*;
import com.pollsSystem.pollsSystem.repository.PollAnswerRepository;
import com.pollsSystem.pollsSystem.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PollAnswerServiceImpl implements PollAnswerService{
    @Autowired
    PollAnswerRepository pollAnswerRepository;

    @Override
    public void createPollAnswer(PollAnswer pollAnswer) {//check if the user and the question is valid
        String userName = userExternalService.getUserNameById(pollAnswer.getUserId());
        Question question =questionService.getQuestionById(pollAnswer.getQuestionId());
        if(userName!= null && question!= null)
        pollAnswerRepository.createPollAnswer(pollAnswer);
    }

    @Override
    public PollAnswer getPollAnswerById(Long id) {
        return pollAnswerRepository.getPollAnswerById(id);
    }

    @Override
    public List<PollAnswer> getAllPollAnswers() {
        return pollAnswerRepository.getAllPollAnswers();
    }

    @Override
    public void updatePollAnswerById(Long pollAnswerId, PollAnswer pollAnswer) {
pollAnswerRepository.updatePollAnswerById(pollAnswerId, pollAnswer);
    }

    @Override
    public void deletePollAnswer(Long id) throws Exception {
        if (pollAnswerRepository.getPollAnswerById(id) != null)
            pollAnswerRepository.deletePollAnswer(id);
        else
            throw new Exception("poll answer is not exists");

    }
    @Override
    public List<PollAnswer> getPollAnswersByUserId(Long userId) {
        return pollAnswerRepository.getPollAnswersByUserId(userId);
    }

    @Override
    public void deletePollAnswersByQuestionId(Long questionId) {
         pollAnswerRepository.deletePollAnswersByQuestionId(questionId);
    }
//דרישה 1
    @Lazy
@Autowired
private QuestionService questionService;
    @Override
    public QuestionWithAnswers getQuestionWithAnswerCounts(Long questionId) {
        // הבאת פרטי השאלה
        QuestionWithAnswers questionWithAnswers = questionService.getQuestionWithAnswers(questionId);

        // הבאת מספר התשובות לכל אופציה
        Map<String, Integer> answerCounts = pollAnswerRepository.getAnswerCountsByQuestionId(questionId);

        // עדכון כמות התשובות ב-QuestionWithAnswers
        if (questionWithAnswers != null) {
            answerCounts.forEach(questionWithAnswers::addAnswerCount);
        }

        return questionWithAnswers;
    }
    //3
    @Autowired
    private UserExternalService userExternalService;

    @Override
    public List<UserQuestionAnswerResponse> getAllPollAnswersByUserId(Long userId) {
        // שליפת שם המשתמש משירות היוזר דרך Feign Client
        String userName = userExternalService.getUserNameById(userId);

        // שליפת התשובות של המשתמש
        List<UserQuestionAnswerResponse> responses = pollAnswerRepository.getAllPollAnswersByUserId(userId);

        // הוספת שם המשתמש לכל תשובה
        for (UserQuestionAnswerResponse response : responses) {
            response.setUserName(userName);
        }

        return responses;
    }
    //4
    @Override
    public UserQuestionCountResponse getUserQuestionCount(Long userId) {
        // שימוש ב-Feign Client לשליפת שם המשתמש
        String userName = userExternalService.getUserNameById(userId);

        // שימוש ברפוזיטורי לשליפת מספר השאלות שהמשתמש ענה עליהן
        int questionCount = pollAnswerRepository.countDistinctQuestionsByUserId(userId);

        // יצירת אובייקט התשובה
        UserQuestionCountResponse response = new UserQuestionCountResponse();
        response.setUserName(userName);
        response.setQuestionCount(questionCount);

        return response;
    }
    //5
//    @Autowired
//    PollAnswerService pollAnswerService;
    @Override
    public List<QuestionWithAnswerCounts> getAllQuestionsWithAnswerCounts() {
        // קבלת כל השאלות
        List<Question> questions = questionService.getAllQuestions2();

        // קבלת מספר התשובות לכל תשובה עבור כל שאלה
        Map<Long, Map<String, Integer>> answerCountsByQuestionId = getAnswerCountsByAllQuestions();

        // בניית התשובות לשאלות
        List<QuestionWithAnswerCounts> result = new ArrayList<>();
        for (Question question : questions) {
            QuestionWithAnswerCounts questionWithAnswerCounts = new QuestionWithAnswerCounts();
            questionWithAnswerCounts.setId(question.getId());
            questionWithAnswerCounts.setTitle(question.getTitle());
            questionWithAnswerCounts.setFirstAnswer(question.getFirstAnswer());
            questionWithAnswerCounts.setSecondAnswer(question.getSecondAnswer());
            questionWithAnswerCounts.setThirdAnswer(question.getThirdAnswer());
            questionWithAnswerCounts.setFourthAnswer(question.getFourthAnswer());

            // הוספת ספירות התשובות
            Map<String, Integer> answerCounts = answerCountsByQuestionId.getOrDefault(question.getId(), new HashMap<>());
            questionWithAnswerCounts.setAnswerCounts(answerCounts);

            result.add(questionWithAnswerCounts);
        }

        return result;
    }

    @Override
    public Map<Long, Map<String, Integer>> getAnswerCountsByAllQuestions() {
        return pollAnswerRepository.getAnswerCountsByAllQuestions();
    }
}

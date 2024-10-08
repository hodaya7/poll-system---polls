package com.pollsSystem.pollsSystem.service;

import com.pollsSystem.pollsSystem.model.*;

import java.util.List;
import java.util.Map;

public interface PollAnswerService {
    void createPollAnswer(PollAnswer pollAnswer);
    PollAnswer getPollAnswerById(Long id);
    List<PollAnswer> getAllPollAnswers();
    void updatePollAnswerById(Long PollAnswerId, PollAnswer pollAnswer);
    void deletePollAnswer(Long id) throws Exception;
     List<PollAnswer> getPollAnswersByUserId(Long userId);
    //    If a question is deleted from your system, all the answers should be deleted as well
    void deletePollAnswersByQuestionId(Long questionId);
    /**
     * מחזיר את השאלה עם מספר התשובות לכל אופציה
     * @param questionId מזהה השאלה
     * @return אובייקט QuestionWithAnswers עם פרטי השאלה ונתוני התשובות
     */
    QuestionWithAnswers getQuestionWithAnswerCounts(Long questionId);
//3
public List<UserQuestionAnswerResponse> getAllPollAnswersByUserId(Long userId);
//4
public UserQuestionCountResponse getUserQuestionCount(Long userId);
    //5
    public List<QuestionWithAnswerCounts> getAllQuestionsWithAnswerCounts();
    //5
    public Map<Long, Map<String, Integer>> getAnswerCountsByAllQuestions();
}

package com.pollsSystem.pollsSystem.repository;

import com.pollsSystem.pollsSystem.model.PollAnswer;
import com.pollsSystem.pollsSystem.model.UserQuestionAnswerResponse;

import java.util.List;
import java.util.Map;

public interface PollAnswerRepository {
    void createPollAnswer(PollAnswer pollAnswer);
    PollAnswer getPollAnswerById(Long id);
    List<PollAnswer> getAllPollAnswers();
    void updatePollAnswerById(Long pollAnswerId, PollAnswer pollAnswer);
    void deletePollAnswer(Long id) throws Exception;
    public List<PollAnswer> getPollAnswersByUserId(Long userId);
    public void deletePollAnswersByQuestionId(Long questionId);
    public Map<String, Integer> getAnswerCountsByQuestionId(Long questionId);
    //3
    public List<UserQuestionAnswerResponse> getAllPollAnswersByUserId(Long userId);
    //4
    public int countDistinctQuestionsByUserId(Long userId);
    //5
    Map<Long, Map<String, Integer>> getAnswerCountsByAllQuestions();
}

package com.pollsSystem.pollsSystem.repository;

import com.pollsSystem.pollsSystem.model.PollAnswer;
import com.pollsSystem.pollsSystem.model.UserQuestionAnswerResponse;
import com.pollsSystem.pollsSystem.repository.mapper.PollAnswerMapper;
import com.pollsSystem.pollsSystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PollAnswerRepositoryImpl implements PollAnswerRepository {
    private static final String POLL_ANSWER_TABLE_NAME = "poll_answer";
    @Autowired
    private JdbcTemplate jdbcTemplate;//הקלאיינט h2 הגיע מהגראדל ששמנו
    @Autowired
    private PollAnswerMapper pollAnswerMapper;

    @Override
    public void createPollAnswer(PollAnswer pollAnswer) {
        String sql = "INSERT INTO " + POLL_ANSWER_TABLE_NAME + " (user_id, question_id, date_time, selected_answer) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, pollAnswer.getUserId(), pollAnswer.getQuestionId(), pollAnswer.getDateTime(), pollAnswer.getSelectedAnswer().name());
    }

    @Override
    public PollAnswer getPollAnswerById(Long id) {
        String sql = "SELECT * FROM " + POLL_ANSWER_TABLE_NAME + " WHERE answer_number=?";
        try {
            //מה יכנס בסימן שאלה:
            return jdbcTemplate.queryForObject(sql, pollAnswerMapper, id);
        } catch (EmptyResultDataAccessException error) {//אם אין תשובה כזו
            return null;
        }
    }

    @Override
    public List<PollAnswer> getAllPollAnswers() {
        String sql = "SELECT * FROM " + POLL_ANSWER_TABLE_NAME;
        try {
            return jdbcTemplate.query(sql, pollAnswerMapper);
        } catch (EmptyResultDataAccessException error) {
            return null;
        }
    }

    @Override
    public void updatePollAnswerById(Long pollAnswerId, PollAnswer pollAnswer) {
        String sql = "UPDATE " + POLL_ANSWER_TABLE_NAME + " SET user_id=?, question_id=?, date_time=?, selected_answer=? WHERE answer_number=?";
        jdbcTemplate.update(sql, pollAnswer.getUserId(), pollAnswer.getQuestionId(), pollAnswer.getDateTime(), pollAnswer.getSelectedAnswer().name(), pollAnswerId);
    }

    @Override
    public void deletePollAnswer(Long id) {
        String sql = "DELETE FROM " + POLL_ANSWER_TABLE_NAME + " WHERE answer_number=?";
        jdbcTemplate.update(sql, id);
    }
    @Override
    public List<PollAnswer> getPollAnswersByUserId(Long userId) {
        String sql = "SELECT * FROM " + POLL_ANSWER_TABLE_NAME + " WHERE user_id=?";
        try {
            return jdbcTemplate.query(sql, pollAnswerMapper, userId);
        } catch (EmptyResultDataAccessException error) {
            return null;
        }
    }
    @Override
    public void deletePollAnswersByQuestionId(Long questionId) {
        String sql = "DELETE FROM " + POLL_ANSWER_TABLE_NAME + " WHERE question_id=?";
        jdbcTemplate.update(sql, questionId);
    }

    //דרישה 1
    //יחזור כמה משתמשים בחרו כל תשובה לשאלה זו
    @Override
    public Map<String, Integer> getAnswerCountsByQuestionId(Long questionId) {
        String sql = "SELECT selected_answer, COUNT(DISTINCT user_id) AS user_count " +
                "FROM " + POLL_ANSWER_TABLE_NAME + " " +
                "WHERE question_id=? " +
                "GROUP BY selected_answer";
        return jdbcTemplate.query(sql, rs -> {
            Map<String, Integer> answerCounts = new HashMap<>();
            while (rs.next()) {
                String selectedAnswer = rs.getString("selected_answer");
                int userCount = rs.getInt("user_count");
                answerCounts.put(selectedAnswer, userCount);
            }
            return answerCounts;
        }, questionId);
    }
    //3
    @Override
    public List<UserQuestionAnswerResponse> getAllPollAnswersByUserId(Long userId) {
        String sql = "SELECT pa.selected_answer, q.title AS question_title " +
                "FROM poll_answer pa " +
                "JOIN question q ON pa.question_id = q.id " +
                "WHERE pa.user_id = ?";

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            UserQuestionAnswerResponse response = new UserQuestionAnswerResponse();
            response.setQuestionTitle(rs.getString("question_title"));
            response.setSelectedAnswer(rs.getString("selected_answer"));
            return response;
        });
    }
//4
@Override
public int countDistinctQuestionsByUserId(Long userId) {
    // SQL לשליפת מספר השאלות הייחודיות שהמשתמש ענה עליהן
    String sql = "SELECT COUNT(DISTINCT question_id) FROM poll_answer WHERE user_id = ?";
    return jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);
}
    //5
    @Override
    public Map<Long, Map<String, Integer>> getAnswerCountsByAllQuestions() {
        String sql = "SELECT question_id, selected_answer, COUNT(*) AS count " +
                "FROM poll_answer " +
                "GROUP BY question_id, selected_answer";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        Map<Long, Map<String, Integer>> answerCountsByQuestionId = new HashMap<>();

        for (Map<String, Object> row : rows) {
            Long questionId = ((Number) row.get("question_id")).longValue();
            String selectedAnswer = (String) row.get("selected_answer");
            Integer count = ((Number) row.get("count")).intValue();

            answerCountsByQuestionId
                    .computeIfAbsent(questionId, k -> new HashMap<>())
                    .put(selectedAnswer, count);
        }

        return answerCountsByQuestionId;
    }
}

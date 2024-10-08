package com.pollsSystem.pollsSystem.repository;

import com.pollsSystem.pollsSystem.model.Question;
import com.pollsSystem.pollsSystem.model.QuestionWithAnswers;
import com.pollsSystem.pollsSystem.model.QuestionWithTotalAnswers;
import com.pollsSystem.pollsSystem.repository.mapper.PollAnswerMapper;
import com.pollsSystem.pollsSystem.repository.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {
    private static final String QUESTION_TABLE_NAME = "question";
    @Autowired
    private JdbcTemplate jdbcTemplate;//הקלאיינט h2 הגיע מהגראדל ששמנו

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void createQuestion(Question question) {
        String sql = "INSERT INTO " + QUESTION_TABLE_NAME + " (title, first_answer, second_answer, third_answer, fourth_answer) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, question.getTitle(), question.getFirstAnswer(), question.getSecondAnswer(), question.getThirdAnswer(), question.getFourthAnswer());
    }

    @Override
    public Question getQuestionById(Long id) {
        String sql = "SELECT * FROM " + QUESTION_TABLE_NAME + " WHERE id=?";
        try {
            //מה יכנס בסימן שאלה:
            return jdbcTemplate.queryForObject(sql, questionMapper, id);
        } catch (EmptyResultDataAccessException error) {//אם אין תשובה כזו
            return null;
        }
    }

    @Override
    public List<Question> getAllQuestions() {
        String sql = "SELECT * FROM " + QUESTION_TABLE_NAME;
        try {
            return jdbcTemplate.query(sql, questionMapper);
        } catch (EmptyResultDataAccessException error) {
            return null;
        }
    }

    @Override
    public void updateQuestionById(Long questionId, Question question) {
        String sql = "UPDATE " + QUESTION_TABLE_NAME + " SET title=?, first_answer=?, second_answer=?, third_answer=?, fourth_answer=? WHERE id=?";
        jdbcTemplate.update(sql, question.getTitle(), question.getFirstAnswer(), question.getSecondAnswer(), question.getThirdAnswer(), question.getFourthAnswer(), questionId);
    }

    @Override
    public void deleteQuestion(Long id) {
        String sql = "DELETE FROM " + QUESTION_TABLE_NAME + " WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    //דרישה 1
    @Override
    public QuestionWithAnswers getQuestionWithAnswers(Long questionId) {
        String sql = "SELECT q.id, q.title, q.first_answer, q.second_answer, q.third_answer, q.fourth_answer, " +
                "COALESCE(COUNT(DISTINCT pa.user_id), 0) AS answer_count, pa.selected_answer " +
                "FROM " + QUESTION_TABLE_NAME + " q " +
                "LEFT JOIN poll_answer pa ON q.id = pa.question_id " +
                "WHERE q.id = ? " +
                "GROUP BY q.id, q.title, q.first_answer, q.second_answer, q.third_answer, q.fourth_answer, pa.selected_answer";
        return jdbcTemplate.query(sql, rs -> {
            QuestionWithAnswers questionWithAnswers = new QuestionWithAnswers();
            while (rs.next()) {
                questionWithAnswers.setId(rs.getLong("id"));
                questionWithAnswers.setTitle(rs.getString("title"));
                questionWithAnswers.setFirstAnswer(rs.getString("first_answer"));
                questionWithAnswers.setSecondAnswer(rs.getString("second_answer"));
                questionWithAnswers.setThirdAnswer(rs.getString("third_answer"));
                questionWithAnswers.setFourthAnswer(rs.getString("fourth_answer"));
                questionWithAnswers.addAnswerCount(rs.getString("selected_answer"), rs.getInt("answer_count"));
            }
            return questionWithAnswers;
        }, questionId);
    }
    //דרישה 2
    private static final String POLL_ANSWER_TABLE_NAME = "poll_answer";
    public QuestionWithTotalAnswers getQuestionWithTotalAnswers(Long questionId) {
        String sql = "SELECT q.id, q.title, q.first_answer, q.second_answer, q.third_answer, q.fourth_answer, " +
                "COALESCE(COUNT(DISTINCT pa.user_id), 0) AS total_answers " +
                "FROM " + QUESTION_TABLE_NAME + " q " +
                "LEFT JOIN " + POLL_ANSWER_TABLE_NAME + " pa ON q.id = pa.question_id " +
                "WHERE q.id = ? " +
                "GROUP BY q.id, q.title, q.first_answer, q.second_answer, q.third_answer, q.fourth_answer";

        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                QuestionWithTotalAnswers questionWithTotalAnswers = new QuestionWithTotalAnswers();
                questionWithTotalAnswers.setId(rs.getLong("id"));
                questionWithTotalAnswers.setTitle(rs.getString("title"));
                questionWithTotalAnswers.setFirstAnswer(rs.getString("first_answer"));
                questionWithTotalAnswers.setSecondAnswer(rs.getString("second_answer"));
                questionWithTotalAnswers.setThirdAnswer(rs.getString("third_answer"));
                questionWithTotalAnswers.setFourthAnswer(rs.getString("fourth_answer"));
                questionWithTotalAnswers.setTotalAnswers(rs.getInt("total_answers"));
                return questionWithTotalAnswers;
            }
            return null;
        }, questionId);
    }
    //5

    @Override
    public List<Question> getAllQuestions2() {
        String sql = "SELECT * FROM " + QUESTION_TABLE_NAME;
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Question question = new Question();
                question.setId(rs.getLong("id"));
                question.setTitle(rs.getString("title"));
                question.setFirstAnswer(rs.getString("first_answer"));
                question.setSecondAnswer(rs.getString("second_answer"));
                question.setThirdAnswer(rs.getString("third_answer"));
                question.setFourthAnswer(rs.getString("fourth_answer"));
                return question;
            });
        } catch (EmptyResultDataAccessException error) {
            return null;
        }
    }
    //5
    @Override
    public Map<Long, Map<String, Integer>> getAnswerCountsByAllQuestions() {
        String sql = "SELECT question_id, selected_answer, COUNT(DISTINCT user_id) AS user_count " +
                "FROM " + POLL_ANSWER_TABLE_NAME + " " +
                "GROUP BY question_id, selected_answer";

        return jdbcTemplate.query(sql, rs -> {
            Map<Long, Map<String, Integer>> result = new HashMap<>();
            while (rs.next()) {
                Long questionId = rs.getLong("question_id");
                String selectedAnswer = rs.getString("selected_answer");
                int userCount = rs.getInt("user_count");

                result.computeIfAbsent(questionId, k -> new HashMap<>()).put(selectedAnswer, userCount);
            }
            return result;
        });
    }
}

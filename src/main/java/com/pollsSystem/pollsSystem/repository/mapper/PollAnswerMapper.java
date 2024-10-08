package com.pollsSystem.pollsSystem.repository.mapper;

import com.pollsSystem.pollsSystem.model.AnswerOption;
import com.pollsSystem.pollsSystem.model.PollAnswer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class PollAnswerMapper implements RowMapper<PollAnswer> {
    @Override
    public PollAnswer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PollAnswer(
                rs.getLong("answer_number"),
                rs.getLong("user_id"),
                rs.getLong("question_id"),
                rs.getTimestamp("date_time").toLocalDateTime(),
                AnswerOption.valueOf(rs.getString("selected_answer")));
    }
}

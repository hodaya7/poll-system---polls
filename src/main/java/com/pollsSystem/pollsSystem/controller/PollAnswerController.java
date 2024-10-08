package com.pollsSystem.pollsSystem.controller;

import com.pollsSystem.pollsSystem.model.PollAnswer;
import com.pollsSystem.pollsSystem.model.UserQuestionAnswerResponse;
import com.pollsSystem.pollsSystem.model.UserQuestionCountResponse;
import com.pollsSystem.pollsSystem.service.PollAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pollAnswer")
public class PollAnswerController {
    @Autowired
    PollAnswerService pollAnswerService;
    @PostMapping("/create")
    public void createPollAnswer(@RequestBody PollAnswer pollAnswer) {
        pollAnswerService.createPollAnswer(pollAnswer);
    }
//    {ex דוגמא שמכניסים
//        "userId": 1,
//            "questionId": 2,
//            "dateTime": "2024-08-07T14:30:00",
//            "selectedAnswer": "A"
//    }
    @PostMapping("/update/{answerNumber}")
    public void updatePollAnswerById(@PathVariable Long answerNumber, @RequestBody PollAnswer pollAnswer) {
        pollAnswerService.updatePollAnswerById(answerNumber, pollAnswer);
    }

    @DeleteMapping("/delete/{answerNumber}")
    public void deletePollAnswerById(@PathVariable Long answerNumber) throws Exception {
        pollAnswerService.deletePollAnswer(answerNumber);
    }

    @GetMapping("/{answerNumber}")
    public PollAnswer getPollAnswerById(@PathVariable Long answerNumber) {
        return pollAnswerService.getPollAnswerById(answerNumber);
    }

    @GetMapping("/all")
    public List<PollAnswer> getAllPollAnswers() {
        return pollAnswerService.getAllPollAnswers();
    }
// עבור זה If a user is deleted from your system, all the answers he gave to any
//    question should be deleted as well.
    @GetMapping("/all/{userId}")
    public List<PollAnswer> getPollAnswersByUserId(@PathVariable Long userId) {
        return pollAnswerService.getPollAnswersByUserId(userId);
    }
    //3
//    By passing the user id → Return the user answer to each question he
//    submitted, and the user-name and the title of each quesiton
    @GetMapping("/user/{userId}")
    public List<UserQuestionAnswerResponse> getAllAnswersByUserId(@PathVariable Long userId) {
        return pollAnswerService.getAllPollAnswersByUserId(userId);
    }
    //דוגמא למה שחוזר
//    [
//    {
//        "userName": "yotam Doe",
//            "questionTitle": "What fav col?",
//            "selectedAnswer": "A"
//    },
//    {
//        "userName": "yotam Doe",
//            "questionTitle": "What fav tv?",
//            "selectedAnswer": "B"
//    }
//]

    //4
//http://localhost:8082/pollAnswer/user/1/question-count
//    By passing the user id → Return how many questions this user
//    answered to, and the user name

    @GetMapping("/user/{userId}/question-count")
    public ResponseEntity<UserQuestionCountResponse> getUserQuestionCount(@PathVariable Long userId) {
        try {
            // קריאה לשירות כדי לקבל את מספר השאלות שהמשתמש ענה עליהן ושם המשתמש
            UserQuestionCountResponse response = pollAnswerService.getUserQuestionCount(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // טיפול בשגיאות במקרה של userId לא תקין או בעיות אחרות
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
//    {
//        "userName": "yoav hg",
//            "questionCount": 2
//    }

}

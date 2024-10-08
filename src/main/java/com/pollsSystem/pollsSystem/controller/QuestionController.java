package com.pollsSystem.pollsSystem.controller;

import com.pollsSystem.pollsSystem.model.*;
import com.pollsSystem.pollsSystem.service.PollAnswerService;
import com.pollsSystem.pollsSystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping("/create")
    public void createQuestion(@RequestBody Question question) {
        questionService.createQuestion(question);
    }
//    {ex
//        "title": "What is your tv?",
//            "firstAnswer": "sam",
//            "secondAnswer": "lg",
//            "thirdAnswer": "gg",
//            "fourthAnswer": "hh"
//    }
    @PostMapping("/update/{questionId}")
    public void updateQuestionById(@PathVariable Long questionId, @RequestBody Question question) {
        questionService.updateQuestionById(questionId, question);
    }

    @DeleteMapping("/delete/{questionId}")
    public void deleteQuestionById(@PathVariable Long questionId) throws Exception {
        questionService.deleteQuestion(questionId);
    }

    @GetMapping("/{questionId}")
    public Question getQuestionById(@PathVariable Long questionId) {
        return questionService.getQuestionById(questionId);
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }
    //דרישה 1
//    By passing the question id → Return how many users choose each of
//    the question options, and the question itself + possible answers
    @Autowired
    private PollAnswerService pollAnswerService;

    /**
     * Retrieves a question with the count of answers for each option.
     *
     * @param questionId the ID of the question to retrieve
     * @return a {@link QuestionWithAnswers} object containing the question details and answer counts
     */
    @GetMapping("/{questionId}/details")
    public QuestionWithAnswers getQuestionWithAnswerCounts(@PathVariable Long questionId) {
        return pollAnswerService.getQuestionWithAnswerCounts(questionId);
    }
    //דוגמא למה שחוזר
//    {
//        "id": 2,
//            "title": "What fav tv?",
//            "firstAnswer": "lg",
//            "secondAnswer": "sam",
//            "thirdAnswer": "len",
//            "fourthAnswer": "app",
//            "answerCounts": {
//        "B": 1,
//                "C": 1
//    }
//    }



    //דרישה 2
//    By passing the question id → Return how many users answer to this
//    question in total, and the question itself
    @GetMapping("/{questionId}/total-answers")
    public QuestionWithTotalAnswers getQuestionWithTotalAnswers(@PathVariable Long questionId) {
        return questionService.getQuestionWithTotalAnswers(questionId);
    }
    //דוגמא למה שחוזר
//    {
//        "id": 2,
//            "title": "What fav tv?",
//            "firstAnswer": "lg",
//            "secondAnswer": "sam",
//            "thirdAnswer": "len",
//            "fourthAnswer": "app",
//            "totalAnswers": 2
//    }



    //5
//    Return all questions and for each question how many users choose
//    each of the question options, and the question title, and each option
    @GetMapping("/with-answer-counts")
    public ResponseEntity<List<QuestionWithAnswerCounts>> getAllQuestionsWithAnswerCounts() {
        List<QuestionWithAnswerCounts> questionsWithAnswerCounts = pollAnswerService.getAllQuestionsWithAnswerCounts();
        return ResponseEntity.ok(questionsWithAnswerCounts);
    }
//דוגמא לחזרה
//    [
//    {
//        "id": 1,
//            "title": "What fav tel?",
//            "firstAnswer": "lg",
//            "secondAnswer": "sam",
//            "thirdAnswer": "app",
//            "fourthAnswer": "lev",
//            "answerCounts": {
//        "A": 2,
//        "D": 1
//    }
//    },
//    {
//        "id": 2,
//            "title": "What fav col?",
//            "firstAnswer": "bla",
//            "secondAnswer": "or",
//            "thirdAnswer": "blu",
//            "fourthAnswer": "red",
//            "answerCounts": {
//        "C": 2
//    }
//    },
//    {
//        "id": 3,
//            "title": "What fav name?",
//            "firstAnswer": "ori",
//            "secondAnswer": "david",
//            "thirdAnswer": "jon",
//            "fourthAnswer": "uri",
//            "answerCounts": {
//        "B": 1
//    }
//    }
//]
}

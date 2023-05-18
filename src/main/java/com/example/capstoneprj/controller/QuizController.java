package com.example.capstoneprj.controller;


import com.example.capstoneprj.domain.dto.AnswerDTO;
import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Quiz;
import com.example.capstoneprj.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    @Autowired
    public QuizService quizService;

    @GetMapping("/get")
    public ResponseEntity<Quiz> get(){
        return new ResponseEntity<>(quizService.getQuestion(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Quiz> save( @RequestBody Quiz quiz){
        return new ResponseEntity<>(quizService.save(quiz),HttpStatus.OK);
    }

    @PostMapping("/validate/{id}")
    public ResponseEntity<Boolean> valid(@PathVariable("id") String id, @RequestBody AnswerDTO answer){
//        boolean isCorrect = quizService.validateAnswer(id, answer);
//        return ResponseEntity.ok(isCorrect);

        return new ResponseEntity<>(quizService.validateAnswer(id, answer),HttpStatus.OK);
    }
}

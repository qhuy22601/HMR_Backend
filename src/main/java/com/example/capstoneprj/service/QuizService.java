package com.example.capstoneprj.service;

import com.example.capstoneprj.domain.dto.AnswerDTO;
import com.example.capstoneprj.domain.dto.ResponseDTO;
import com.example.capstoneprj.domain.model.Quiz;
import com.example.capstoneprj.repository.QuizRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class QuizService {

    @Autowired
    public QuizRepo quizRepo;


    public Quiz save(Quiz quiz){
        return quizRepo.save(quiz);
    }

    public Quiz getQuestion(){
        List<Quiz> listQuiz = quizRepo.findAll();
        if(!listQuiz.isEmpty()){
            int randomIndex = new Random().nextInt(listQuiz.size());
            return listQuiz.get(randomIndex);
        }
        return null;
    }

    public boolean validateAnswer(String id, AnswerDTO answer){
        Optional<Quiz> optionalQuiz = quizRepo.findById(id);
        if(optionalQuiz.isPresent()){
            Quiz quiz = optionalQuiz.get();
            return quiz.getAnswer().equalsIgnoreCase(answer.getAnswer());
        }
        return false;
    }
}

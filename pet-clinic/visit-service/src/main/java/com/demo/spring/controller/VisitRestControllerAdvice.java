package com.demo.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demo.spring.util.Message;

@RestControllerAdvice
public class VisitRestControllerAdvice {
	
//	@ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<Message> handleUserNotFoundException(RuntimeException une) {
//        return ResponseEntity.ok(new Message("Something wrong. Please try again later"));
//    }
}

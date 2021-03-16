package org.tyaa.demo.java.springboot.brokershop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tyaa.demo.java.springboot.brokershop.models.FeedbackModel;
import org.tyaa.demo.java.springboot.brokershop.models.ResponseModel;
import org.tyaa.demo.java.springboot.brokershop.services.FeedbackService;
import org.tyaa.demo.java.springboot.brokershop.services.interfaces.IFeedbackService;

@RestController
@RequestMapping("/api")
public class FeedbackController {
    private final FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<ResponseModel> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping("/feedbacks")
    public ResponseEntity<ResponseModel> create(@RequestBody FeedbackModel feedback) {
        return new ResponseEntity<>(service.create(feedback), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/feedbacks/{id}")
    public ResponseEntity<ResponseModel> update(@PathVariable Long id, @RequestBody FeedbackModel feedback) {
        feedback.setId(id);
        return new ResponseEntity<>(service.update(feedback), HttpStatus.OK);
    }

    @DeleteMapping(value = "/feedbacks/{id}")
    public ResponseEntity<ResponseModel> deleteCategory(@PathVariable Long id) {
        ResponseModel responseModel = service.delete(id);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}

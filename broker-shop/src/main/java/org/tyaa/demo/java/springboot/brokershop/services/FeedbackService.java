package org.tyaa.demo.java.springboot.brokershop.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.tyaa.demo.java.springboot.brokershop.entities.Feedback;
import org.tyaa.demo.java.springboot.brokershop.models.FeedbackModel;
import org.tyaa.demo.java.springboot.brokershop.models.ResponseModel;
import org.tyaa.demo.java.springboot.brokershop.repositories.FeedbackDao;
import org.tyaa.demo.java.springboot.brokershop.services.interfaces.IFeedbackService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackService {
    private final FeedbackDao feedbackDao;

    public FeedbackService(FeedbackDao feedbackDao) {
        this.feedbackDao = feedbackDao;
    }

    // создать новый отзыв на основе полученной модели
    public ResponseModel create(FeedbackModel feedbackModel) {
        // преобразование из модели в сущность
        Feedback feedback =
                Feedback.builder().name(feedbackModel.getName().trim()).build();
        // вызов метода сохранения сущности экземпляром репозитория
        feedbackDao.save(feedback);
        // Demo Logging
        System.out.println(String.format("Feedback %s Created", feedback.getName()));
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("Feedback %s Created", feedback.getName()))
                .build();
    }

    public ResponseModel update(FeedbackModel feedbackModel) {
        Feedback feedback =
                Feedback.builder()
                        .id(feedbackModel.getId())
                        .name(feedbackModel.getName())
                        .email(feedbackModel.getEmail())
                        .subject(feedbackModel.getSubject())
                        .text(feedbackModel.getText())
                        .build();
        feedbackDao.save(feedback);
        // Demo Logging
        System.out.println(String.format("Feedback %s Updated", feedback.getName()));
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("Feedback %s Updated", feedback.getName()))
                .build();
    }

    public ResponseModel getAll() {
        List<Feedback> feedbacks = feedbackDao.findAll(Sort.by("id").descending());
        List<FeedbackModel> feedbackModels =
                feedbacks.stream()
                        .map(f ->
                                FeedbackModel.builder()
                                        .id(f.getId())
                                        .name(f.getName())
                                        .email(f.getEmail())
                                        .subject(f.getSubject())
                                        .text(f.getText())
                                        .build()
                        )
                        .collect(Collectors.toList());
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .data(feedbackModels)
                .build();
    }

    public ResponseModel delete(Long id) {
        Optional<Feedback> feedbackOptional = feedbackDao.findById(id);
        if (feedbackOptional.isPresent()){
            Feedback feedback = feedbackOptional.get();
            // System.out.println(productDao.countProductsByCategory(category) == 0);
            // if(productDao.countProductsByCategory(category) == 0) {
            feedbackDao.delete(feedback);
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .message(String.format("Feedback #%s Deleted", feedback.getName()))
                    .build();
            /* } else {
                return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Can't delete the category #%s. There are some products in this category.", category.getName()))
                    .build();
            } */
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Feedback #%d Not Found", id))
                    .build();
        }
    }
}

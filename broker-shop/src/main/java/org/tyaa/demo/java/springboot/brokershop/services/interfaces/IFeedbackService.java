package org.tyaa.demo.java.springboot.brokershop.services.interfaces;

import org.tyaa.demo.java.springboot.brokershop.models.FeedbackModel;
import org.tyaa.demo.java.springboot.brokershop.models.ResponseModel;

public interface IFeedbackService {
    ResponseModel create(FeedbackModel feedbackModel);
    ResponseModel update(FeedbackModel feedbackModel);
    ResponseModel getAll();
    ResponseModel delete(Long id);
}

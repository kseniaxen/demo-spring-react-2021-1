package org.tyaa.demo.java.springboot.brokershop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tyaa.demo.java.springboot.brokershop.entities.Feedback;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback, Long> {
}

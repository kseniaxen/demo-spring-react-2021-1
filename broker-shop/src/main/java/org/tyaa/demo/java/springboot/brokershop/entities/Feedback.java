package org.tyaa.demo.java.springboot.brokershop.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Feedbacks")
@Data
@EqualsAndHashCode(exclude = "feedbacks")
@ToString(exclude = "feedbacks")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "subject", nullable = false, length = 50)
    private String subject;
    @Column(name = "text", nullable = false, length = 500)
    private String text;
}

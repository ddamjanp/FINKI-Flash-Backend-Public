package com.flash.finki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "api_logs")
public class ApiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Column(name = "request_payload", columnDefinition = "jsonb", nullable = false)
    private String requestPayload;

    @Column(name = "response_payload", columnDefinition = "jsonb", nullable = false)
    private String responsePayload;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
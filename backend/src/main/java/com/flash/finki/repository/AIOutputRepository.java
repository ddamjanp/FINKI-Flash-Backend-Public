package com.flash.finki.repository;

import com.flash.finki.model.AIOutput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIOutputRepository extends JpaRepository<AIOutput, Long> {

    List<AIOutput> findByFileId(Long fileId);
}
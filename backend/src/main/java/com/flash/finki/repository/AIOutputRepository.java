package com.flash.finki.repository;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIOutputRepository extends JpaRepository<AIOutput, Long> {
    AIOutput findByFile(File file);
}
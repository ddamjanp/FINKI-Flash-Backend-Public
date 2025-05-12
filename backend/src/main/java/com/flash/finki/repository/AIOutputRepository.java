package com.flash.finki.repository;

import com.flash.finki.model.AIOutput;
import com.flash.finki.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIOutputRepository extends JpaRepository<AIOutput, Long> {
    AIOutput findByFile(File file);

    List<AIOutput> findAllByFileId(Long id);

    List<AIOutput> findByFileId(Long fileId);
}
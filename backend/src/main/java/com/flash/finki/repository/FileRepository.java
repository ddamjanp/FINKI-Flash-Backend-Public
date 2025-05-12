package com.flash.finki.repository;

import com.flash.finki.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByFilename(String filename);
}
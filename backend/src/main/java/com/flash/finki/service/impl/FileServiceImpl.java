package com.flash.finki.service.impl;

import com.flash.finki.model.File;
import com.flash.finki.repository.FileRepository;
import com.flash.finki.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public List<File> getAllByUserId(Long userId) {
        return fileRepository.findAllByUserId(userId);
    }
}

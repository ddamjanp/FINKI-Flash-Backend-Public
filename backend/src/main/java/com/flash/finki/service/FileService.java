package com.flash.finki.service;

import com.flash.finki.model.File;

import java.util.List;

public interface FileService {
    
    List<File> getAllByUserId(Long userId);
}

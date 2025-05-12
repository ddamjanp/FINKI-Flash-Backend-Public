package com.flash.finki.service.impl;

import com.flash.finki.model.AIOutput;
import com.flash.finki.repository.AIOutputRepository;
import com.flash.finki.service.AIOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIOutputServiceImpl implements AIOutputService {

    @Autowired
    AIOutputRepository aioutputRepository;

    @Override
    public List<AIOutput> getAllByFileId(Long fileId) {
        return aioutputRepository.findAllByFileId(fileId);
    }
}

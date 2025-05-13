package com.flash.finki.service;

import com.flash.finki.model.AIOutput;

import java.util.List;

public interface AIOutputService {

    List<AIOutput> getAllByFileId(Long fileId);
}

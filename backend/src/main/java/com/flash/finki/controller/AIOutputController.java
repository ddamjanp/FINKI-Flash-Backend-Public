package com.flash.finki.controller;

import com.flash.finki.model.AIOutput;
import com.flash.finki.service.AIOutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/aioutput")
public class AIOutputController {

    @Autowired
    AIOutputService AIOutputService;

    @GetMapping("/{fileId}")
    public ResponseEntity<List<AIOutput>> getAllByFileId(@PathVariable Long fileId){
        List<AIOutput> files = AIOutputService.getAllByFileId(fileId);
        System.out.println("Fetched files for user " + fileId + ": " + files.size() + " files found.");
        return ResponseEntity.ok(files);
    }


}

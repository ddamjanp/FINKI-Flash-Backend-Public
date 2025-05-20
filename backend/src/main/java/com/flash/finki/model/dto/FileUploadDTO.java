package com.flash.finki.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadDTO {

    private String message;

    private Long fileID;
}

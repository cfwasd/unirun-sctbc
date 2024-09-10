package com.example.unirun.controller;

import com.example.unirun.dto.NewRecordModel;
import com.example.unirun.service.NewRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * @author wzh
 */
@RestController
public class NewRecord {

    @Autowired
    private NewRecordService service;

    @PostMapping("/run")
    public String addNewRecord(@RequestBody NewRecordModel model) throws NoSuchAlgorithmException, JsonProcessingException {
        return service.record(model);
    }
}

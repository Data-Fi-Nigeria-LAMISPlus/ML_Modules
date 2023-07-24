package org.lamisplus.modules.ml.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/machine-learning")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MlController {

    @GetMapping("")
    public String welcomeApi(){
        return "Welcome to LAMISPlus machine learning module";
    }
}

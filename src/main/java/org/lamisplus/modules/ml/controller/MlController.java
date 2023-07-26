package org.lamisplus.modules.ml.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.ml.requestDto.HtsMlRequestDTO;
import org.lamisplus.modules.ml.service.ModelService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/v1/machine-learning")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MlController {
private final  ModelService modelService;
    /**
     * API end point for evaluating an ML model
     * @requestBody htsMlRequestDTO
     * @return variable-value pair of the prediction details
     */
    @RequestMapping(method = RequestMethod.POST, value = "/evaluate")
    @ResponseBody
    public Object processModel(@Valid  @RequestBody HtsMlRequestDTO htsMlRequestDTO) {
        return modelService.getHtsEvaluationScoreAndPrediction(htsMlRequestDTO);
    }
}

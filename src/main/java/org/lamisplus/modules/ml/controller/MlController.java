package org.lamisplus.modules.ml.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.node.ObjectNode;
import org.json.simple.JSONObject;
import org.lamisplus.modules.ml.domain.ModelInputFields;
import org.lamisplus.modules.ml.domain.ScoringResult;
import org.lamisplus.modules.ml.service.ModelService;
import org.lamisplus.modules.ml.utils.MLUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("api/v1/machine-learning")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MlController {

    @GetMapping("")
    public String welcomeApi(){
        return "Welcome to LAMISPlus machine learning module";
    }

    /**
     * API end point for evaluating an ML model
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/casefindingscore")
    @ResponseBody
    public Object processModel(HttpServletRequest request) {
        ModelService modelService = new ModelService();
        String requestBody = null;
        try {
            System.out.println("incoming" + request.getReader());
            requestBody = MLUtils.fetchRequestBody(request.getReader());
            System.out.println("body " + request.getReader());
            ObjectNode modelConfigs = MLUtils.getModelConfig(requestBody);
            String facilityMflCode = modelConfigs.get(MLUtils.FACILITY_ID_REQUEST_VARIABLE).asText();
            boolean isDebugMode = modelConfigs.has("debug") && modelConfigs.get("debug").asText().equals("true") ? true
                    : false;

            if (facilityMflCode.equals("")) { // TODO: this should reflect how facilities are identified in LAMISPlus
                facilityMflCode = MLUtils.getDefaultMflCode();
            }

            String modelId = modelConfigs.get(MLUtils.MODEL_ID_REQUEST_VARIABLE).asText();
            String encounterDate = modelConfigs.get(MLUtils.ENCOUNTER_DATE_REQUEST_VARIABLE).asText();

            if (StringUtils.isBlank(facilityMflCode) || StringUtils.isBlank(modelId) || StringUtils.isBlank(encounterDate)) {
                return new ResponseEntity<Object>("The service requires model, date, and facility information",
                        new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            JSONObject profile = MLUtils.getHTSFacilityProfile("SiteCode", facilityMflCode, MLUtils.getFacilityCutOffs());

            if (profile == null) {
                return new ResponseEntity<Object>(
                        "The facility provided currently doesn't have an HTS cut-off profile. Provide an appropriate facility",
                        new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            ModelInputFields inputFields = MLUtils.extractHTSCaseFindingVariablesFromRequestBody(requestBody, facilityMflCode,
                    encounterDate);

            ScoringResult scoringResult = modelService.score(modelId, facilityMflCode, encounterDate, inputFields, isDebugMode);
            return scoringResult;
        }
        catch (IOException e) {
            return new ResponseEntity<Object>("Could not process the request", new HttpHeaders(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

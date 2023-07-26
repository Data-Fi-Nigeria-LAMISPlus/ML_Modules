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

            requestBody = MLUtils.fetchRequestBody(request.getReader());
            System.out.println("body " + request.getReader());


            requestBody = "{ \"modelConfigs\": {\n" +
                    "\t        \"modelId\":\"hts_v1\", \"encounterDate\":\"2021-06-05\",\n" +
                    "\t        \"facilityId\":\"LBgwDTw2C8u\", \"debug\":\"true\" }, \"variableValues\": {\n" +
                    "\t        \"age\" : 34,\n" +
                    "\t\t\"sex_M\" : 0,\n" +
                    "\t\t\"sex_F\" : 1,\n" +
                    "\t\t\"first_time_visit_Y\" : 1,\n" +
                    "\t\t\"referred_from_Self\" : 1,\n" +
                    "\t\t\"referred_from_Other\" : 0,\n" +
                    "\t\t\"marital_status_Married\" : 1,\n" +
                    "\t\t\"marital_status_Divorced\" : 0,\n" +
                    "\t\t\"marital_status_Widowed\" : 0,\n" +
                    "\t\t\"session_type_Individual\" : 1,\n" +
                    "\t\t\"previously_tested_hiv_negative_Missing\" : 0,\n" +
                    "\t\t\"previously_tested_hiv_negative_TRUE.\" : 0,\n" +
                    "\t\t\"client_pregnant_X0\" : 0,\n" +
                    "\t\t\"hts_setting_Others\" : 0,\n" +
                    "\t\t\"hts_setting_Outreach\" : 1,\n" +
                    "\t\t\"hts_setting_Other\" : 0,\n" +
                    "\t\t\"tested_for_hiv_before_within_this_year_NotPreviouslyTested\" : 1,\n" +
                    "\t\t\"tested_for_hiv_before_within_this_year_PreviouslyTestedNegative\" : 0,\n" +
                    "\t\t\"tested_for_hiv_before_within_this_year_PreviouslyTestedPositiveInHIVCare\" : 0,\n" +
                    "\t\t\"tested_for_hiv_before_within_this_year_PreviouslyTestedPositiveNotInHIVCare\" : 0 } }";

            ObjectNode modelConfigs = MLUtils.getModelConfig(requestBody);

            System.out.println("incoming  " + modelConfigs);

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
            JSONObject profile = MLUtils.getHTSFacilityProfile("Facility.Datim.ID", facilityMflCode, MLUtils.getFacilityCutOffs());

            System.out.println("incoming  " + profile);

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

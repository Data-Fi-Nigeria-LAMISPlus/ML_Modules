package org.lamisplus.modules.ml.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.lamisplus.modules.ml.requestDto.HtsMlRequestDTO;
import org.lamisplus.modules.ml.requestDto.ModelConfigs;
import org.lamisplus.modules.ml.domain.ModelInputFields;
import org.lamisplus.modules.ml.domain.ScoringResult;
import org.lamisplus.modules.ml.service.ModelService;
import org.lamisplus.modules.ml.utils.MLUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * @param mlRequestDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/evaluate")
    @ResponseBody
    public Object processModel(@Valid  @RequestBody HtsMlRequestDTO mlRequestDTO) {
        ModelService modelService = new ModelService();
        ObjectMapper mapper = new ObjectMapper();
        try {
            ModelConfigs modelConfigs1 = mlRequestDTO.getModelConfigs();
//            System.out.println("incoming" + request.getReader());
//            requestBody = MLUtils.fetchRequestBody(request.getReader());
//            System.out.println("body " + request.getReader());
            //ObjectNode modelConfigs = MLUtils.getModelConfig(requestBody);
            String facilityMflCode = modelConfigs1.getFacilityId();
            String debug = modelConfigs1.getDebug();
            boolean isDebugMode = debug.equals("true");
            String requestBody =  mapper.writeValueAsString(mlRequestDTO);
            LOG.info("HTS Ml Request data {}", requestBody);
            if (facilityMflCode != null && StringUtils.isBlank(facilityMflCode)) {
                // TODO: this should reflect how facilities are identified in LAMISPlus
                facilityMflCode = MLUtils.getDefaultMflCode();
            }
            String modelId = modelConfigs1.getModelId();
            String encounterDate = modelConfigs1.getEncounterDate();

            if (StringUtils.isBlank(facilityMflCode) || StringUtils.isBlank(modelId) || StringUtils.isBlank(encounterDate)) {
                return new ResponseEntity<Object>("The service requires model, date, and facility information",
                        new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            JSONObject profile = MLUtils.getHTSFacilityProfile("Facility.Datim.ID", facilityMflCode, MLUtils.getFacilityCutOffs());

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
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Object>("Could not process the request", new HttpHeaders(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}

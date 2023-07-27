package org.lamisplus.modules.ml.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.Computable;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.jpmml.evaluator.OutputField;
import org.json.simple.JSONObject;
import org.lamisplus.modules.ml.domain.ModelInputFields;
import org.lamisplus.modules.ml.domain.ScoringResult;
import org.lamisplus.modules.ml.exception.ScoringException;
import org.lamisplus.modules.ml.requestDto.HtsMlRequestDTO;
import org.lamisplus.modules.ml.requestDto.ModelConfigs;
import org.lamisplus.modules.ml.utils.MLUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class used to prepare and score models
 */
@Service
@Slf4j
public class ModelService {

	private Log log = LogFactory.getLog(this.getClass());
	private Map<String, Object> cache = new HashMap<>();

	public ScoringResult score(String modelId, String facilityName, String encounterDate, ModelInputFields inputFields,
							   boolean debug) {
		try {
			String fullModelFileName = modelId.concat(".pmml");
			InputStream stream = ModelService.class.getClassLoader().getResourceAsStream(fullModelFileName);

			// Building a model evaluator from a PMML file
			Evaluator evaluator = new LoadingModelEvaluatorBuilder().load(stream).build();
			evaluator.verify();
			ScoringResult scoringResult = new ScoringResult(score(evaluator, inputFields, debug));
			return scoringResult;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception during preparation of input parameters or scoring of values for HTS model. "
					+ e.getMessage());
			throw new ScoringException("Exception during preparation of input parameters or scoring of values", e);
		}
	}

	/**
	 * A method that scores a model
	 *
	 * @param evaluator
	 * @param inputFields
	 * @return
	 */
	private Map<String, Object> score(Evaluator evaluator, ModelInputFields inputFields, boolean debug) {
		Map<String, Object> result = new HashMap<String, Object>();

		Map<FieldName, ?> evaluationResultFromEvaluator = evaluator.evaluate(prepareEvaluationArgs(evaluator, inputFields));

		List<OutputField> outputFields = evaluator.getOutputFields();
		//List<TargetField> targetFields = evaluator.getTargetFields();

		for (OutputField targetField : outputFields) {
			FieldName targetFieldName = targetField.getName();
			Object targetFieldValue = evaluationResultFromEvaluator.get(targetField.getName());

			if (targetFieldValue instanceof Computable) {
				targetFieldValue = ((Computable) targetFieldValue).getResult();
			}

			result.put(targetFieldName.getValue(), targetFieldValue);
		}
		//TODO: this is purely for debugging
		if (debug) {
			Map<String, Object> modelInputs = new HashMap<String, Object>();
			Map<String, Object> combinedResult = new HashMap<String, Object>();
			for (Map.Entry<String, Object> entry : inputFields.getFields().entrySet()) {
				modelInputs.put(entry.getKey(), entry.getValue());
			}
			combinedResult.put("predictions", result);
			combinedResult.put("ModelInputs", modelInputs);

			return combinedResult;
		} else {
			return result;
		}
	}

	/**
	 * Performs variable mapping
	 *
	 * @param evaluator
	 * @param inputFields
	 * @return variable-value pair
	 */
	private Map<FieldName, FieldValue> prepareEvaluationArgs(Evaluator evaluator, ModelInputFields inputFields) {
		Map<FieldName, FieldValue> arguments = new LinkedHashMap<FieldName, FieldValue>();

		List<InputField> evaluatorFields = evaluator.getActiveFields();

		for (InputField evaluatorField : evaluatorFields) {
			FieldName evaluatorFieldName = evaluatorField.getName();
			String evaluatorFieldNameValue = evaluatorFieldName.getValue();

			Object inputValue = inputFields.getFields().get(evaluatorFieldNameValue);

			if (inputValue == null) {
				log.warn("Model value not found for the following field: " + evaluatorFieldNameValue);
			}

			arguments.put(evaluatorFieldName, evaluatorField.prepare(inputValue));
		}
		return arguments;
	}

	public Object getHtsEvaluationScoreAndPrediction(HtsMlRequestDTO mlRequestDTO) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ModelConfigs modelConfigs1 = mlRequestDTO.getModelConfigs();
//            System.out.println("incoming" + request.getReader());
//            requestBody = MLUtils.fetchRequestBody(request.getReader());
//            System.out.println("body " + request.getReader());
			//ObjectNode modelConfigs = MLUtils.getModelConfig(requestBody);
			String facilityMflCode = modelConfigs1.getFacilityId();
			String debug = modelConfigs1.getDebug();
			boolean isDebugMode = debug.equals("true");
			//String requestBody =  mapper.writeValueAsString(mlRequestDTO);
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
			return getResultFromCacheOrModel(mlRequestDTO, facilityMflCode, encounterDate, modelId, mapper, isDebugMode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>("Could not process the request", new HttpHeaders(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}


	public Object getResultFromCacheOrModel(HtsMlRequestDTO payload,
											String facilityMflCode,
											String encounterDate,
											String modelId,
											ObjectMapper objectMapper,
											boolean isDebugMode) {
		try {
			String hash = calculateHash(objectMapper.writeValueAsString(payload.getVariableValues()));
			if (hash != null && cache.containsKey(hash)) {
				return cache.get(hash);
			} else {
				// If the hash is not in the cache, call the ML model and store the result in the cache
				ModelInputFields inputFields =
						MLUtils.extractHTSCaseFindingVariablesFromRequestBody(objectMapper.writeValueAsString(payload),
								facilityMflCode, encounterDate);
				ScoringResult scoringResult = score(modelId, facilityMflCode, encounterDate, inputFields, isDebugMode);
				cache.put(hash, scoringResult);
				return scoringResult;
			}
		}catch (Exception e){
			return new ResponseEntity<Object>("Could not process the request", new HttpHeaders(),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	private String calculateHash(String payload) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(payload.getBytes());
			StringBuilder hashBuilder = new StringBuilder();
			for (byte b : hashBytes) {
				hashBuilder.append(String.format("%02x", b));
			}
			return hashBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}


}

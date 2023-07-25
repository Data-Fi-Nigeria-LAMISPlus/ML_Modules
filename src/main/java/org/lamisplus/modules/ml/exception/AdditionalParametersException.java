package org.lamisplus.modules.ml.exception;

public class AdditionalParametersException extends KeJPMMLEvaluatorException {
	
	private static final long serialVersionUID = 5123858921619908538L;
	
	public AdditionalParametersException(String message) {
		super(message);
	}
	
	public AdditionalParametersException(String message, Throwable cause) {
		super(message, cause);
	}
}

	private UpdateValueStrategy<String, Object> loggingModelToTargetStrategy(int updatePolicy) {
		return new UpdateValueStrategy<String, Object>(updatePolicy)
				.setAfterGetValidator(loggingValidator(log, "model-get"))
				.setConverter(loggingConverter(log, "model-convert"))
				.setAfterConvertValidator(loggingValidator(log, "model-after-convert"))
				.setBeforeSetValidator(loggingValidator(log, "target-before-set"));

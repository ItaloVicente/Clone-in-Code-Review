	private UpdateValueStrategy loggingTargetToModelStrategy(int updatePolicy) {
		return new UpdateValueStrategy(updatePolicy).setAfterGetValidator(
				loggingValidator(log, "target-get")).setConverter(
				loggingConverter(log, "target-convert"))
				.setAfterConvertValidator(
						loggingValidator(log, "target-after-convert"))
				.setBeforeSetValidator(
						loggingValidator(log, "model-before-set"));

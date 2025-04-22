	private IValidator loggingValidator(final List log, final String message) {
		return new IValidator() {
			@Override
			public IStatus validate(Object value) {
				log.add(message);
				return ValidationStatus.ok();
			}

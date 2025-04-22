	private IValidator warningValidator() {
		return new IValidator() {
			@Override
			public IStatus validate(Object value) {
				return ValidationStatus.warning("");
			}
		};

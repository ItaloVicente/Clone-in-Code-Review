	private IValidator cancelValidator() {
		return new IValidator() {
			@Override
			public IStatus validate(Object value) {
				return ValidationStatus.cancel("");
			}
		};

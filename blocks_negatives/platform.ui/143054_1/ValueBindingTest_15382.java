	private IValidator infoValidator() {
		return new IValidator() {
			@Override
			public IStatus validate(Object value) {
				return ValidationStatus.info("");
			}
		};

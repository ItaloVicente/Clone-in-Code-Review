	private IValidator errorValidator() {
		return new IValidator() {
			@Override
			public IStatus validate(Object value) {
				return ValidationStatus.error("");
			}
		};

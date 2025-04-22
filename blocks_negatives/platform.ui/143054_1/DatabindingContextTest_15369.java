		IValidator validator = new IValidator() {
			@Override
			public IStatus validate(Object value) {
				return ValidationStatus.error(errorMessage);
			}
		};

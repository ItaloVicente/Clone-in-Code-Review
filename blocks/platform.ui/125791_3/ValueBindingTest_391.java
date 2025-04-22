		return new IValidator<Object>() {
			@Override
			public IStatus validate(Object value) {
				return ValidationStatus.info("");
			}
		};

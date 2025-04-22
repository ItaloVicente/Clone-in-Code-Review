		IValidator<String> validator = new IValidator<String>() {
			@Override
			public IStatus validate(String value) {
				return ValidationStatus.error(errorMessage);
			}
		};

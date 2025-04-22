		return new IValidator<Integer>() {
			@Override
			public IStatus validate(Integer value) {
				if (value < 1 || value > 20) {
					return ValidationStatus.error("Max number of people must be between 1 and 20 inclusive");
				}
				return null;

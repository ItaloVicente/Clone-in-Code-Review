		IValidator<String> vowelValidator = new IValidator<String>() {
			@Override
			public IStatus validate(String value) {
				if (!value.matches("[aeiouAEIOU]*")) {
					return ValidationStatus.error("only vowels allowed");
				}
				return Status.OK_STATUS;

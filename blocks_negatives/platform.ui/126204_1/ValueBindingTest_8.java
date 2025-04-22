		return new IValidator<Object>() {
			@Override
			public IStatus validate(Object value) {
				log.add(message);
				return ValidationStatus.ok();
			}

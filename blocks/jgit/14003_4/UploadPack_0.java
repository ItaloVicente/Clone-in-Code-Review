	public void setRequestValidator(RequestValidator validator) {
		requestValidator = validator;
		requestPolicy = validator != null ? RequestPolicy.CUSTOM
				: RequestPolicy.ADVERTISED;
	}


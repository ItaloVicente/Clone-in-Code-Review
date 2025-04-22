		Binder3.modelToTargetValue() //
				.model(value) //
				.useDefaultConvertion() //
				.target(text(SWT.Modify).observe(text)) //
				.validateTargetAfterGet(fiveValidator) //
				.convertOnly() //
				.bind(bindingContext);


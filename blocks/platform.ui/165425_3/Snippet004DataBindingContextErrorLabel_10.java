		IValidator<Integer> fiveValidator = v -> v == 5 ? ok() : error("the value was '" + v + "', not '5'");

		Binder3.twoWayValue() //
				.model(value) //
				.convertToTarget(i -> Objects.toString(i, "")) //
				.convertToModel(s -> s.isBlank() ? 0 : Integer.decode(s)) //
				.target(WidgetProperties.text(SWT.Modify).observe(text)) //
				.validateModelBeforeSet(fiveValidator) //
				.bind(bindingContext);

		Binder3.modelToTargetValue() //
				.model(value) //
				.useDefaultConvertion() //
				.target(text(SWT.Modify).observe(text)) //
				.validateTargetAfterGet(fiveValidator) //
				.convertOnly() //
				.bind(bindingContext);

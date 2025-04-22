		IValidator<Integer> fiveValidator = v -> v == 5 ? ok() : error("the value was '" + v + "', not '5'");

		Bind.twoWayValue() //
				.model(value) //
				.convertToTarget(i -> Objects.toString(i, "")) //
				.convertToModel(s -> s.isEmpty() ? 0 : Integer.decode(s)) //
				.target(WidgetProperties.text(SWT.Modify).observe(text)) //
				.validateModelBeforeSet(fiveValidator) //
				.bind(bindingContext);

		Bind.oneWayValue() //
				.model(value) //
				.useDefaultConvertion() //
				.target(WidgetProperties.text(SWT.Modify).observe(text)) //
				.validateTargetAfterGet(fiveValidator) //
				.convertOnlyToTarget() //
				.bind(bindingContext);

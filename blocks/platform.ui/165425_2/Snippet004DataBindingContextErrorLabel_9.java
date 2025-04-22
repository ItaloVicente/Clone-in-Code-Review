		Binder3.twoWayValue() //
				.model(value) //
				.validateModel(v -> v == 5 ? Status.OK_STATUS
						: ValidationStatus.error("the value was '" + v + "', not '5'")) //
				.convertToTarget(IConverter.create(i -> Objects.toString(i, ""))) //
				.convertToModel(IConverter.create(s -> s.isBlank() ? 0 : Integer.decode(s))) //
				.target(WidgetProperties.text(SWT.Modify).observe(text)) //
				.validateTarget(v -> v.matches(".*\\d+.*") ? Status.OK_STATUS
						: ValidationStatus.error("Not a number: '" + v + "'"))
				.bind(bindingContext);

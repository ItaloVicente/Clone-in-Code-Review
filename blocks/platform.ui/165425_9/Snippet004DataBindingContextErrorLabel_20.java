		IValidator<Integer> fiveValidator = v -> v != null && v == 5 ? ok()
				: error("the value was '" + v + "', not '5'");

		Bind.twoWay() //
				.from(value) //
				.validateTwoWay(fiveValidator) //
				.convertTo(IConverter.create(i -> Objects.toString(i, ""))) //
				.convertFrom(IConverter.create(s -> s.isEmpty() ? 0 : Integer.decode(s))) //
				.to(WidgetProperties.text(SWT.Modify).observe(text)) //
				.validateTwoWay(v -> v.matches("\\s*\\d+\\s*") ? Status.OK_STATUS
						: error("Not a number: '" + v + "'"))
				.bind(bindingContext);

		Bind.oneWay() //
				.from(value) //
				.validateAfterGet(fiveValidator) //
				.defaultConvertTo(WidgetProperties.text(SWT.Modify).observe(text)) //
				.convertOnly() //
				.bind(bindingContext);

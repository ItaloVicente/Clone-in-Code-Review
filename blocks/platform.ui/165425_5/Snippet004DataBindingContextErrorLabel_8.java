		Binder3.oneWayValue() //
				.model(new AggregateValidationStatus(bindingContext.getBindings(),
						AggregateValidationStatus.MAX_SEVERITY)) //
				.defaultConverter()
				.target(WidgetProperties.text().observe(errorLabel)) //
				.bind(bindingContext);

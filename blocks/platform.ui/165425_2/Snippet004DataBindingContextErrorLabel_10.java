		Binder3.oneWayValue() //
				.model(new AggregateValidationStatus(bindingContext.getBindings(),
						AggregateValidationStatus.MAX_SEVERITY)) //
				.useDefaultConvertion() // Lets use use default object-to-string-conversion
				.target(WidgetProperties.text().observe(errorLabel)) //
				.bind(bindingContext);

		Bind.oneWayValue() //
				.model(new AggregateValidationStatus(bindingContext.getBindings(),
						AggregateValidationStatus.MAX_SEVERITY)) //
				.useDefaultConvertion() // Lets use use default object-to-string-conversion
				.target(WidgetProperties.text().observe(errorLabel)) //
				.bind(bindingContext);

		Button zeroButton = new Button(shell, SWT.NONE);
		zeroButton.setText("Set zero!");
		zeroButton.addListener(SWT.Selection, e -> value.setValue(0));

		Button fiveButton = new Button(shell, SWT.NONE);
		fiveButton.setText("Set five!");
		fiveButton.addListener(SWT.Selection, e -> value.setValue(5));

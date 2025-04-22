			bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(text), value,
					new UpdateValueStrategy<String, String>().setAfterConvertValidator(new FiveValidator()), null);

			bindingContext.bindValue(WidgetProperties.text().observe(errorLabel),
					new AggregateValidationStatus(bindingContext.getBindings(), AggregateValidationStatus.MAX_SEVERITY));

			shell.pack();
			shell.open();

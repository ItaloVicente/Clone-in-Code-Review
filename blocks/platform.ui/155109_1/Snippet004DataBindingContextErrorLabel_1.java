	private static Shell createShell() {
		Shell shell = new Shell();
		shell.setText("Data Binding Snippet 004");
		shell.setLayout(new GridLayout(2, false));

		new Label(shell, SWT.NONE).setText("Enter '5' to be valid:");

		Text text = new Text(shell, SWT.BORDER);
		WritableValue<String> value = WritableValue.withValueType(String.class);
		new Label(shell, SWT.NONE).setText("Error:");

		Label errorLabel = new Label(shell, SWT.BORDER);
		errorLabel.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).applyTo(errorLabel);

		DataBindingContext bindingContext = new DataBindingContext();

		bindingContext.bindValue(WidgetProperties.text(SWT.Modify).observe(text), value,
				new UpdateValueStrategy<String, String>().setAfterConvertValidator(new FiveValidator()), null);

		bindingContext.bindValue(WidgetProperties.text().observe(errorLabel),
				new AggregateValidationStatus(bindingContext.getBindings(), AggregateValidationStatus.MAX_SEVERITY));

		shell.pack();
		shell.open();
		return shell;
	}


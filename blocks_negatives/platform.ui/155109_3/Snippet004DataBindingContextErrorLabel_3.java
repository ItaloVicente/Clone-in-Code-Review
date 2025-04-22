		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Shell(display);
			shell.setText("Data Binding Snippet 004");
			shell.setLayout(new GridLayout(2, false));

			new Label(shell, SWT.NONE).setText("Enter '5' to be valid:");

			Text text = new Text(shell, SWT.BORDER);
			WritableValue<String> value = WritableValue.withValueType(String.class);
			new Label(shell, SWT.NONE).setText("Error:");

			Label errorLabel = new Label(shell, SWT.BORDER);
			errorLabel.setForeground(display.getSystemColor(SWT.COLOR_RED));
			GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).applyTo(errorLabel);

			DataBindingContext bindingContext = new DataBindingContext();

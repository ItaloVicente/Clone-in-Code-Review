		Label resultLabel = new Label(main, SWT.NONE);
		resultLabel.setText(UIText.RebaseResultDialog_StatusLabel);
		Text resultText = new Text(main, SWT.BORDER);
		resultText.setText(result.getStatus().toString());
		resultText.setEditable(false);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resultText);

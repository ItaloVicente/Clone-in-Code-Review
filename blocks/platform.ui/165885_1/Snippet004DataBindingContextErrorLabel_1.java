		Button zeroButton = new Button(shell, SWT.NONE);
		zeroButton.setText("Set zero!");
		zeroButton.addListener(SWT.Selection, e -> value.setValue(0));

		Button fiveButton = new Button(shell, SWT.NONE);
		fiveButton.setText("Set five!");
		fiveButton.addListener(SWT.Selection, e -> value.setValue(5));

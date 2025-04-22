		Label storeLabel = new Label(composite, SWT.NONE);
		storeLabel.setText(UIText.LoginDialog_storeInSecureStore);
		storeCheckbox = new Button(composite, SWT.CHECK);
		storeCheckbox.setSelection(true);
		if (changeCredentials)
			storeCheckbox.setEnabled(false);


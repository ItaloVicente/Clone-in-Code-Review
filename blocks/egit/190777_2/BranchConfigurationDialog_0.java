		Label pushRemoteLabel = new Label(main, SWT.NONE);
		pushRemoteLabel
				.setText(UIText.BranchConfigurationDialog_PushRemoteLabel);
		pushRemoteText = new Combo(main, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(pushRemoteText);


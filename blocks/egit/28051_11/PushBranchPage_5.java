
		Group remoteGroup = new Group(inputPanel, SWT.NONE);
		remoteGroup.setText(UIText.PushBranchPage_Destination);
		remoteGroup.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().applyTo(remoteGroup);

		Label remoteLabel = new Label(remoteGroup, SWT.NONE);

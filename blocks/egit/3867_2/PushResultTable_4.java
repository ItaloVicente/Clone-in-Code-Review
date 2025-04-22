		Group messageGroup = new Group(root, SWT.NONE);
		messageGroup.setText(UIText.PushResultTable_MesasgeText);
		GridLayoutFactory.swtDefaults().applyTo(messageGroup);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(messageGroup);

		final Text text = new Text(messageGroup, SWT.MULTI | SWT.READ_ONLY

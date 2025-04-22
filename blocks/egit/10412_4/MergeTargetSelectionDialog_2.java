		Group mergeTypeGroup = new Group(main, SWT.NONE);
		mergeTypeGroup
				.setText(UIText.MergeTargetSelectionDialog_MergeTypeGroup);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(mergeTypeGroup);
		mergeTypeGroup.setLayout(new GridLayout(1, false));

		Button commit = new Button(mergeTypeGroup, SWT.RADIO);

		authorHandler = UIUtils.addPreviousValuesContentProposalToText(
				authorText, AUTHOR_VALUES_PREF);
		new Label(personArea, SWT.LEFT).setText(UIText.CommitDialog_Committer);
		committerText = new Text(personArea, SWT.BORDER);
		committerText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());

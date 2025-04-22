
		Group actionGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(actionGroup);
		actionGroup.setText(UIText.RebaseResultDialog_ActionGroupTitle);
		actionGroup.setLayout(new GridLayout(1, false));

		startMergeButton = new Button(actionGroup, SWT.RADIO);
		startMergeButton.setText(UIText.RebaseResultDialog_StartMergeRadioText);
		abortRebaseButton = new Button(actionGroup, SWT.RADIO);
		abortRebaseButton
				.setText(UIText.RebaseResultDialog_AbortRebaseRadioText);
		doNothingButton = new Button(actionGroup, SWT.RADIO);
		doNothingButton.setText(UIText.RebaseResultDialog_DoNothingRadioText);
		startMergeButton.setSelection(true);

		commitGroup.pack();
		applyDialogFont(main);


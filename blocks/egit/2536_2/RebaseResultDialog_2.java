		nextStepsGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(nextStepsGroup);
		nextStepsGroup.setText(UIText.RebaseResultDialog_NextSteps);
		nextStepsGroup.setLayout(new GridLayout(1, false));
		final TextViewer nextSteps = new TextViewer(nextStepsGroup, SWT.MULTI
				| SWT.BORDER | SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 60)
				.applyTo(nextSteps.getControl());
		nextSteps.getTextWidget().setText(
				UIText.RebaseResultDialog_NextStepsAfterResolveConflicts);


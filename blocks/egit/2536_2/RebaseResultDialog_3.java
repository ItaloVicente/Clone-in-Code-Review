		startMergeButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				nextSteps
						.getTextWidget()
						.setText(
								UIText.RebaseResultDialog_NextStepsAfterResolveConflicts);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});

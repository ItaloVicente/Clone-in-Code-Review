		doNothingButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				nextSteps.getTextWidget().setText(
						UIText.RebaseResultDialog_NextStepsDoNothing);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});


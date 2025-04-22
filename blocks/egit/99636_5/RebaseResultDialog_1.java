				if (startMergeButton.getSelection()) {
					nextSteps.getTextWidget().setText(
							UIText.RebaseResultDialog_NextStepsAfterResolveConflicts);
					getButton(getDefaultButtonIndex())
							.setText(IDialogConstants.PROCEED_LABEL);
				}

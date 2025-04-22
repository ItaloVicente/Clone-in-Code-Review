		updateSectionText();
		updateToolbar();

		final ICommitMessageComponentNotifications listener = new ICommitMessageComponentNotifications() {

			public void updateSignedOffToggleSelection(boolean selection) {
				signedOffByAction.setChecked(selection);
			}

			public void updateChangeIdToggleSelection(boolean selection) {
				addChangeIdAction.setChecked(selection);
			}
		};
		commitMessageComponent = new CommitMessageComponent(currentRepository,
				listener);
		commitMessageComponent.attachControls(commitMessageText, authorText,
				committerText);

		horizontalSashForm.setWeights(new int[] { 40, 60 });
		veriticalSashForm.setWeights(new int[] { 50, 50 });

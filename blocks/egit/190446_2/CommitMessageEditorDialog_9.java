
		Text hiddenAuthor = new Text(composite, SWT.NONE);
		GridDataFactory.fillDefaults().exclude(true).applyTo(hiddenAuthor);
		hiddenAuthor.setVisible(false);
		Text hiddenCommitter = new Text(composite, SWT.NONE);
		GridDataFactory.fillDefaults().exclude(true).applyTo(hiddenCommitter);
		hiddenCommitter.setVisible(false);

		UserConfig cfg = config.get(UserConfig.KEY);
		String person = cfg.getCommitterName() + " <" + cfg.getCommitterEmail() //$NON-NLS-1$
				+ '>';
		hiddenAuthor.setText(person);
		hiddenCommitter.setText(person);

		commitComponent = new CommitMessageComponent(repository,
				new ICommitMessageComponentNotifications() {

					@Override
					public void updateSignedOffToggleSelection(
							boolean selection) {
						signOffAction.setChecked(selection);
					}

					@Override
					public void updateSignCommitToggleSelection(
							boolean selection) {
					}

					@Override
					public void updateChangeIdToggleSelection(
							boolean selection) {
						addChangeIdAction.setChecked(selection);
					}

					@Override
					public void statusUpdated() {
					}
				});
		commitComponent.attachControls(messageArea, hiddenAuthor,
				hiddenCommitter);
		commitComponent.updateSignedOffAndChangeIdButton();


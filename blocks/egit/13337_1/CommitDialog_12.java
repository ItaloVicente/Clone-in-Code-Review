		UIUtils.addBulbDecorator(commitText.getTextWidget(),
				UIText.CommitDialog_ContentAssist);

		Composite personArea = toolkit.createComposite(messageAndPersonArea);
		toolkit.paintBordersFor(personArea);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(personArea);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(personArea);

		toolkit.createLabel(personArea, UIText.CommitDialog_Author)
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		authorText = toolkit.createText(personArea, null);
		authorText
				.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		authorText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());

		toolkit.createLabel(personArea, UIText.CommitDialog_Committer)
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		committerText = toolkit.createText(personArea, null);
		committerText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
		if (committer != null)
			committerText.setText(committer);

		amendingItem = new ToolItem(messageToolbar, SWT.CHECK);
		amendingItem.setSelection(amending);
		if (amending)
			amendingItem.setEnabled(false); // if already set, don't allow any
		else if (!amendAllowed)
			amendingItem.setEnabled(false);
		amendingItem.setToolTipText(UIText.CommitDialog_AmendPreviousCommit);
		Image amendImage = UIIcons.AMEND_COMMIT.createImage();
		UIUtils.hookDisposal(amendingItem, amendImage);
		amendingItem.setImage(amendImage);

		signedOffItem = new ToolItem(messageToolbar, SWT.CHECK);

		signedOffItem.setToolTipText(UIText.CommitDialog_AddSOB);
		Image signedOffImage = UIIcons.SIGNED_OFF.createImage();
		UIUtils.hookDisposal(signedOffItem, signedOffImage);
		signedOffItem.setImage(signedOffImage);

		changeIdItem = new ToolItem(messageToolbar, SWT.CHECK);
		Image changeIdImage = UIIcons.GERRIT.createImage();
		UIUtils.hookDisposal(changeIdItem, changeIdImage);
		changeIdItem.setImage(changeIdImage);
		changeIdItem.setToolTipText(UIText.CommitDialog_AddChangeIdLabel);

		final ICommitMessageComponentNotifications listener = new ICommitMessageComponentNotifications() {

			public void updateSignedOffToggleSelection(boolean selection) {
				signedOffItem.setSelection(selection);

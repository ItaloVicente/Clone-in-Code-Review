		Section messageSection = toolkit.createSection(container,
				ExpandableComposite.TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT);
		messageSection.setText(UIText.CommitDialog_CommitMessage);
		Composite messageArea = toolkit.createComposite(messageSection);
		GridLayoutFactory.fillDefaults().spacing(0, 0)
				.extendedMargins(2, 2, 2, 2).applyTo(messageArea);
		toolkit.paintBordersFor(messageArea);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(messageSection);

		Composite headerArea = new Composite(messageSection, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).numColumns(2)
				.applyTo(headerArea);

		ToolBar messageToolbar = new ToolBar(headerArea, SWT.FLAT
				| SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.FILL)
				.grab(true, false).applyTo(messageToolbar);

		addMessageDropDown(headerArea);

		messageSection.setTextClient(headerArea);

		commitText = new SpellcheckableMessageArea(messageArea, commitMessage,
				SWT.NONE);
		commitText
				.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		messageSection.setClient(messageArea);
		Point size = commitText.getTextWidget().getSize();
		int minHeight = commitText.getTextWidget().getLineHeight() * 3;
		commitText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, true).hint(size).minSize(size.x, minHeight)
				.align(SWT.FILL, SWT.FILL).create());
		commitText.setText(calculateCommitMessage());

		commitText.getTextWidget().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.CR
						&& (event.stateMask & SWT.CONTROL) > 0) {
					okPressed();
				} else if (event.keyCode == SWT.TAB
						&& (event.stateMask & SWT.SHIFT) == 0) {
					event.doit = false;
					commitText.traverse(SWT.TRAVERSE_TAB_NEXT);
				}
			}
		});

		Composite personArea = toolkit.createComposite(container);
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
		if (author != null)
			authorText.setText(author);

		authorHandler = UIUtils.addPreviousValuesContentProposalToText(
				authorText, AUTHOR_VALUES_PREF);
		toolkit.createLabel(personArea, UIText.CommitDialog_Committer)
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		committerText = toolkit.createText(personArea, null);
		committerText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
		if (committer != null)
			committerText.setText(committer);
		committerText.addModifyListener(new ModifyListener() {
			String oldCommitter = committerText.getText();

			public void modifyText(ModifyEvent e) {
				if (signedOffItem.getSelection()) {
					String newCommitter = committerText.getText();
					String oldSignOff = getSignedOff(oldCommitter);
					String newSignOff = getSignedOff(newCommitter);
					commitText.setText(replaceSignOff(commitText.getText(),
							oldSignOff, newSignOff));
					oldCommitter = newCommitter;
				}
			}
		});

		committerHandler = UIUtils.addPreviousValuesContentProposalToText(
				committerText, COMMITTER_VALUES_PREF);

		amendingItem = new ToolItem(messageToolbar, SWT.CHECK);
		if (amending) {
			amendingItem.setSelection(amending);
			authorText.setText(previousAuthor);
			saveOriginalChangeId();
		} else if (!amendAllowed) {
			amendingItem.setEnabled(false);
			originalChangeId = null;
		}
		amendingItem.addSelectionListener(new SelectionAdapter() {
			boolean alreadyAdded = false;
			public void widgetSelected(SelectionEvent arg0) {
				if (!amendingItem.getSelection()) {
					originalChangeId = null;
					authorText.setText(author);
				}
				else {
					saveOriginalChangeId();
					if (!alreadyAdded) {
						alreadyAdded = true;
						commitText.setText(previousCommitMessage.replaceAll(
								"\n", Text.DELIMITER)); //$NON-NLS-1$

		Composite messageAndPersonArea = toolkit.createComposite(container);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(messageAndPersonArea);
		GridLayoutFactory.swtDefaults().margins(0, 0).spacing(0, 0)
				.applyTo(messageAndPersonArea);

		Section messageSection = toolkit.createSection(messageAndPersonArea,
				ExpandableComposite.TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT);
		messageSection.setText(UIText.CommitDialog_CommitMessage);
		Composite messageArea = toolkit.createComposite(messageSection);
		GridLayoutFactory.fillDefaults().spacing(0, 0)
				.extendedMargins(2, 2, 2, 2).applyTo(messageArea);
		toolkit.paintBordersFor(messageArea);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(messageSection);
		GridLayoutFactory.swtDefaults().applyTo(messageSection);

		Composite headerArea = new Composite(messageSection, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).numColumns(2)
				.applyTo(headerArea);

		ToolBar messageToolbar = new ToolBar(headerArea, SWT.FLAT
				| SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.FILL)
				.grab(true, false).applyTo(messageToolbar);

		addMessageDropDown(headerArea);

		messageSection.setTextClient(headerArea);

		final CommitProposalProcessor commitProposalProcessor = new CommitProposalProcessor() {
			@Override
			protected Collection<String> computeFileNameProposals() {
				return getFileList();
			}

			@Override
			protected Collection<String> computeMessageProposals() {
				return CommitMessageHistory.getCommitHistory();

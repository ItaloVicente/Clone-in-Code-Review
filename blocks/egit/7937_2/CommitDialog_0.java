		boolean isDetached = false;
		String branch;
		try {
			branch = repository.getBranch();
			if (ObjectId.isId(branch)) {
				branch = NLS.bind(UIText.CommitDialog_DetachedHead, branch.substring(0,7));
				isDetached = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			branch = "[err:" + e.getMessage() + "]";  //$NON-NLS-1$//$NON-NLS-2$
		}
		RepositoryState state = repository.getRepositoryState();

		Section infoSection = toolkit.createSection(container, ExpandableComposite.TITLE_BAR|ExpandableComposite.CLIENT_INDENT);
		infoSection.setText("Repo info"); //$NON-NLS-1$
		Composite infoArea = toolkit.createComposite(container);
		toolkit.paintBordersFor(infoArea);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(infoArea);
		GridDataFactory.fillDefaults().grab(false, false).applyTo(infoArea);

		toolkit.createLabel(infoArea, "Current branch") //$NON-NLS-1$
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		Label branchLabel = toolkit.createLabel(infoArea, null);
		branchLabel.setLayoutData(GridDataFactory.fillDefaults()
				.grab(false, false).minSize(0, 0).create());
		branchLabel.setText(branch);
		Label branchWarningLabel = toolkit.createLabel(infoArea, null);
		branchWarningLabel.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).align(SWT.BEGINNING, SWT.CENTER). create());
		toolkit.createLabel(infoArea, "Repo state") //$NON-NLS-1$
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		if (isDetached) {
			Image warningImage = JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
			branchWarningLabel.setImage(warningImage);
			branchWarningLabel.setToolTipText("You are not on a checked out branch, also known as a 'detached HEAD'.\nUnless you are careful you could lose the resulting commit.\n\nPerhaps you want to checkout or create a branch before committing?"); //$NON-NLS-1$
		}
		Label stateLabel = toolkit.createLabel(infoArea, null);
		stateLabel.setLayoutData(GridDataFactory.fillDefaults()
				.grab(false, false).create());
		stateLabel.setText(state.getDescription());


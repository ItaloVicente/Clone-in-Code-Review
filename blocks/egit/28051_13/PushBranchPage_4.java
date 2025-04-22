		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(inputPanel);

		Composite sourceComposite = new Composite(inputPanel, SWT.NONE);
		sourceComposite.setLayout(new RowLayout());
		Label sourceLabel = new Label(sourceComposite, SWT.NONE);
		sourceLabel.setText(UIText.PushBranchPage_Source);
		Label spacer = new Label(sourceComposite, SWT.NONE);
		spacer.setLayoutData(new RowData(10, SWT.DEFAULT));

		if (this.ref != null) {
			Image branchIcon = UIIcons.BRANCH.createImage();
			this.disposables.add(branchIcon);
			Label branchIconLabel = new Label(sourceComposite, SWT.NONE);
			branchIconLabel.setLayoutData(new RowData(branchIcon.getBounds().width, branchIcon.getBounds().height));
			branchIconLabel.setBackgroundImage(branchIcon);
			Label localBranchLabel = new Label(sourceComposite, SWT.NONE);
			localBranchLabel.setText(Repository.shortenRefName(this.ref
					.getName()));
		}

		Image commitIcon = UIIcons.COMMIT.createImage();
		this.disposables.add(commitIcon);
		Label commitIconLabel = new Label(sourceComposite, SWT.NONE);
		commitIconLabel.setBackgroundImage(commitIcon);
		commitIconLabel.setLayoutData(new RowData(commitIcon.getBounds().width,
				commitIcon.getBounds().height));
		Label commit = new Label(sourceComposite, SWT.NONE);
		RevWalk revWalk = new RevWalk(repository);
		StringBuilder commitBuilder = new StringBuilder(this.commitToPush
				.getName().substring(0, 8));
		StringBuilder commitTooltipBuilder = new StringBuilder(
				this.commitToPush.getName());
		try {
			RevCommit revCommit = revWalk.parseCommit(this.commitToPush);
			commitBuilder.append(' ');
			commitBuilder.append(revCommit.getShortMessage());
			commitTooltipBuilder.append(System.lineSeparator());
			commitTooltipBuilder.append(System.lineSeparator());
			commitTooltipBuilder.append(revCommit.getFullMessage());
		} catch (IOException ex) {
			commitBuilder
					.append(UIText.PushBranchPage_CannotAccessCommitDescription);
			commitTooltipBuilder.append(ex.getMessage());
			Activator.handleError(ex.getLocalizedMessage(), ex, false);
		}
		commit.setText(commitBuilder.toString());
		commit.setToolTipText(commitTooltipBuilder.toString());


		Group remoteGroup = new Group(inputPanel, SWT.NONE);
		remoteGroup.setText(UIText.PushBranchPage_Destination);
		remoteGroup.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().applyTo(remoteGroup);

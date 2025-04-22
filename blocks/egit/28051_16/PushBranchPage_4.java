		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(inputPanel);

		Label sourceLabel = new Label(inputPanel, SWT.NONE);
		sourceLabel.setText(UIText.PushBranchPage_Source);

		Composite sourceComposite = new Composite(inputPanel, SWT.NONE);
		sourceComposite.setLayoutData(GridDataFactory.fillDefaults()
				.indent(UIUtils.getControlIndent(), 0).create());
		RowLayout rowLayout = RowLayoutFactory.fillDefaults().create();
		rowLayout.center = true;
		sourceComposite.setLayout(rowLayout);

		if (this.ref != null) {
			Image branchIcon = UIIcons.BRANCH.createImage();
			this.disposables.add(branchIcon);

			Label branchIconLabel = new Label(sourceComposite, SWT.NONE);
			branchIconLabel
					.setLayoutData(new RowData(branchIcon.getBounds().width,
							branchIcon.getBounds().height));
			branchIconLabel.setBackgroundImage(branchIcon);
			Label localBranchLabel = new Label(sourceComposite, SWT.NONE);
			localBranchLabel.setText(Repository.shortenRefName(this.ref
					.getName()));

			Label spacer = new Label(sourceComposite, SWT.NONE);
			spacer.setLayoutData(new RowData(3, SWT.DEFAULT));
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
				.abbreviate(7).name());
		StringBuilder commitTooltipBuilder = new StringBuilder(
				this.commitToPush.getName());
		try {
			RevCommit revCommit = revWalk.parseCommit(this.commitToPush);
			commitBuilder.append("  "); //$NON-NLS-1$
			commitBuilder.append(revCommit.getShortMessage());
			commitTooltipBuilder.append("\n\n"); //$NON-NLS-1$
			commitTooltipBuilder.append(revCommit.getFullMessage());
		} catch (IOException ex) {
			commitBuilder
					.append(UIText.PushBranchPage_CannotAccessCommitDescription);
			commitTooltipBuilder.append(ex.getMessage());
			Activator.handleError(ex.getLocalizedMessage(), ex, false);
		}
		commit.setText(commitBuilder.toString());
		commit.setToolTipText(commitTooltipBuilder.toString());

		Label destinationLabel = new Label(inputPanel, SWT.NONE);
		destinationLabel.setText(UIText.PushBranchPage_Destination);
		GridDataFactory.fillDefaults().applyTo(destinationLabel);

		Composite remoteGroup = new Composite(inputPanel, SWT.NONE);
		remoteGroup.setLayoutData(GridDataFactory.fillDefaults()
				.indent(UIUtils.getControlIndent(), 0).create());
		remoteGroup.setLayout(GridLayoutFactory.fillDefaults().numColumns(3)

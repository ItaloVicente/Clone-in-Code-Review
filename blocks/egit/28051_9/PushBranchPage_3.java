		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(inputPanel);

		Group sourceGroup = new Group(inputPanel, SWT.NONE);
		sourceGroup.setText(UIText.PushBranchPage_FromLocal);
		sourceGroup.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().applyTo(sourceGroup);

		Label commitLabel = new Label(sourceGroup, SWT.NONE);
		commitLabel.setText(UIText.PushBranchPage_CommitLabel);
		commitLabel
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		Label commit = new Label(sourceGroup, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(commit);
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
			Activator
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, Activator.getDefault()
							.getBundle().getSymbolicName(), ex.getMessage(), ex));
		}
		commit.setText(commitBuilder.toString());
		commit.setToolTipText(commitTooltipBuilder.toString());

		Label localBranchLabel = new Label(sourceGroup, SWT.NONE);
		localBranchLabel.setText(UIText.PushBranchPage_LocalBranchLabel);
		Label localBranch = new Label(sourceGroup, SWT.NONE);
		localBranch.setLayoutData(GridDataFactory.fillDefaults().create());
		if (this.ref != null) {
			localBranch.setText(Repository.shortenRefName(this.ref.getName()));
		}

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
		StringBuilder commitBuilder = new StringBuilder(ref.getObjectId()
				.getName().substring(0, 8));
		StringBuilder commitTooltipBuilder = new StringBuilder(ref
				.getObjectId().getName());
		try {
			RevCommit revCommit = revWalk.parseCommit(ref.getObjectId());
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
		localBranch.setText(Repository.shortenRefName(ref.getName()));

		Group remoteGroup = new Group(inputPanel, SWT.NONE);
		remoteGroup.setText(UIText.PushBranchPage_ToRemote);
		remoteGroup.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().applyTo(remoteGroup);

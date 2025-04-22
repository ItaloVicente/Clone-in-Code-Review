
		Label commitLabel = new Label(inputPanel, SWT.NONE);
		commitLabel.setText(UIText.PushBranchPage_CommitLabel);
		commitLabel
				.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
		Label commit = new Label(inputPanel, SWT.NONE);
		commit.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).create());
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

		Label localBranchLabel = new Label(inputPanel, SWT.NONE);
		localBranchLabel.setText(UIText.PushBranchPage_LocalBranchLabel);
		Label localBranch = new Label(inputPanel, SWT.NONE);
		localBranch.setLayoutData(GridDataFactory.fillDefaults().span(2, 1)
				.create());
		localBranch.setText(Repository.shortenRefName(ref.getName()));

		Label to = new Label(inputPanel, SWT.NONE);
		to.setText(UIText.PushBranchPage_to);
		to.setLayoutData(GridDataFactory.fillDefaults().indent(SWT.DEFAULT, 20)
				.span(3, 1).create());


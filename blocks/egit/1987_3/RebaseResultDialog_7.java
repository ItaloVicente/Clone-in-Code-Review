		Label commitIdLabel = new Label(commitGroup, SWT.NONE);
		commitIdLabel.setText(UIText.RebaseResultDialog_CommitIdLabel);
		Text commitId = new Text(commitGroup, SWT.READ_ONLY | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(commitId);

		Label commitMessageLabel = new Label(commitGroup, SWT.NONE);
		commitMessageLabel
				.setText(UIText.RebaseResultDialog_CommitMessageLabel);
		TextViewer commitMessage = new TextViewer(commitGroup, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.MULTI | SWT.BORDER | SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, 60)
				.applyTo(commitMessage.getControl());

		boolean conflictListFailure = false;
		DirCache dc = null;
		RevWalk rw = null;
		try {
			rw = new RevWalk(repo);
			RevCommit commit = rw.parseCommit(result.getCurrentCommit());
			commitMessage.getTextWidget().setText(commit.getFullMessage());
			commitId.setText(commit.name());
			dc = repo.lockDirCache();
			for (int i = 0; i < dc.getEntryCount(); i++) {
				if (dc.getEntry(i).getStage() > 0)
					conflictPaths.add(dc.getEntry(i).getPathString());

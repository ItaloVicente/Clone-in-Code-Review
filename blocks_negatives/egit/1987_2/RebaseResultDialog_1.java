		if (result.getStatus() == Status.STOPPED) {
			String diff;
			Group commitGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
			GridDataFactory.fillDefaults().span(2, 1).grab(true, true).applyTo(
					commitGroup);
			commitGroup.setText(UIText.RebaseResultDialog_DetailsGroup);
			commitGroup.setLayout(new GridLayout(2, false));
			RevWalk rw = new RevWalk(repo);

			Label commitIdLabel = new Label(commitGroup, SWT.NONE);
			commitIdLabel.setText(UIText.RebaseResultDialog_CommitIdLabel);
			Text commitId = new Text(commitGroup, SWT.READ_ONLY | SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(commitId);

			Label commitMessageLabel = new Label(commitGroup, SWT.NONE);
			commitMessageLabel
					.setText(UIText.RebaseResultDialog_CommitMessageLabel);
			Text commitMessage = new Text(commitGroup, SWT.READ_ONLY
					| SWT.BORDER);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(
					commitMessage);
			try {
				RevCommit commit = rw.parseCommit(result.getCurrentCommit());
				RevCommit parentCommit = rw.parseCommit(commit.getParent(0));
				commitMessage.setText(commit.getShortMessage());
				commitId.setText(commit.name());

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DiffFormatter df = new DiffFormatter(bos);
				df.setRepository(repo);
				try {
					df.format(commit.getTree(), parentCommit.getTree());
				} catch (IOException e) {
					diff = null;
				}
			} catch (Exception e) {
				Activator.handleError(
						UIText.RebaseResultDialog_DiffCalculationErrorMessage,
						e, false);
				diff = UIText.RebaseResultDialog_DiffCalculationErrorDisplay;
			}
			if (diff != null) {
				Label diffLabel = new Label(commitGroup, SWT.NONE);
				diffLabel.setText(UIText.RebaseResultDialog_DiffDetailsLabel);
				GridDataFactory.fillDefaults().span(2, 1).applyTo(diffLabel);
				Text diffArea = new Text(commitGroup, SWT.MULTI | SWT.READ_ONLY
						| SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				Point size = diffArea.getSize();
				int minHeight = diffArea.getLineHeight() * 10;
				GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
						.hint(size).minSize(size.x, minHeight)
						.applyTo(diffArea);
				diffArea.setText(diff);


		Group g2 = new Group(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(g2);
		g2.setLayout(new GridLayout(2, false));
		Label label = new Label(g2, SWT.NONE);
		label.setText(UIText.ResetTargetSelectionDialog_ExpressionLabel);
		anySha1 = new Text(g2, SWT.BORDER);
		anySha1.setToolTipText(UIText.ResetTargetSelectionDialog_ExpressionTooltip);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(anySha1);

		Group g3 = new Group(g2, SWT_NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(g3);
		g3.setLayout(new GridLayout(2, false));
		new Label(g3, SWT.NONE).setText(UIText.ResetTargetSelectionDialog_CommitLabel);
		sha1 = new Label(g3, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(sha1);
		new Label(g3, SWT.NONE).setText(UIText.ResetTargetSelectionDialog_SubjectLabel);
		subject = new Label(g3, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(subject);
		new Label(g3, SWT.NONE).setText(UIText.ResetTargetSelectionDialog_AuthorLabel);
		author = new Label(g3, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(author);
		new Label(g3, SWT.NONE).setText(UIText.ResetTargetSelectionDialog_CommitterLabel);
		committer = new Label(g3, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(committer);


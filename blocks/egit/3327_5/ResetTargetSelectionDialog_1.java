
		Group g2 = new Group(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(g2);
		g2.setLayout(new GridLayout(2, false));
		Label label = new Label(g2, SWT.NONE);
		label.setText("Reset to (expression):"); //$NON-NLS-1$
		anySha1 = new Text(g2, SWT.BORDER);
		anySha1.setToolTipText("Any git expression evaluating to a commit-ish"); //$NON-NLS-1$
		GridDataFactory.fillDefaults().grab(true, false).applyTo(anySha1);

		Group g3 = new Group(g2, SWT_NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(g3);
		g3.setLayout(new GridLayout(2, false));
		new Label(g3, SWT.NONE).setText("SHA-1:"); //$NON-NLS-1$
		sha1 = new Label(g3, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(sha1);
		new Label(g3, SWT.NONE).setText("Subject:"); //$NON-NLS-1$
		subject = new Label(g3, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(subject);
		new Label(g3, SWT.NONE).setText("Author:"); //$NON-NLS-1$
		author = new Label(g3, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(author);
		new Label(g3, SWT.NONE).setText("Committer:"); //$NON-NLS-1$
		committer = new Label(g3, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(committer);


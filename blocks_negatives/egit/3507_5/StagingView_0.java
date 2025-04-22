		parent.setLayout(new GridLayout(1, false));

		repositoryLabel = new Label(parent, SWT.NONE);
		repositoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		SashForm horizontalSashForm = new SashForm(parent, SWT.NONE);
		horizontalSashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite leftHandComposite = new Composite(horizontalSashForm, SWT.NONE);
		leftHandComposite.setLayout(new GridLayout(1, false));

		SashForm veriticalSashForm = new SashForm(leftHandComposite, SWT.VERTICAL);
		veriticalSashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite unstagedComposite = new Composite(veriticalSashForm, SWT.NONE);
		unstagedComposite.setLayout(new GridLayout(1, false));

		SashForm veriticalSashForm = new SashForm(leftHandComposite, SWT.VERTICAL);
		veriticalSashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite unstagedComposite = new Composite(veriticalSashForm, SWT.NONE);
		unstagedComposite.setLayout(new GridLayout(1, false));

		new Label(unstagedComposite, SWT.NONE).setText(UIText.StagingView_UnstagedChanges);

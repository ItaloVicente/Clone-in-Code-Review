		final SashForm mainForm = new SashForm(container, SWT.VERTICAL
				| SWT.FILL);
		mainForm.setLayoutData(GridDataFactory.fillDefaults().grab(true, true)
				.create());
		createMessageAndPersonArea(mainForm);
		filesSection = createFileSection(mainForm);
		mainForm.setWeights(new int[] { 50, 50 });

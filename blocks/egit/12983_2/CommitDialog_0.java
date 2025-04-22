		final SashForm sashForm= new SashForm(container, SWT.VERTICAL
				| SWT.FILL);
		toolkit.adapt(sashForm, true, true);
		sashForm.setLayoutData(GridDataFactory.fillDefaults().grab(true, true)
				.create());
		createMessageAndPersonArea(sashForm);
		filesSection = createFileSection(sashForm);
		sashForm.setWeights(new int[] { 50, 50 });

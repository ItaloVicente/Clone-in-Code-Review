		final FetchResultTable table = new FetchResultTable(composite);
		if (result.getFetchResult() != null)
			table.setData(localDb, result.getFetchResult());
		final Control tableControl = table.getControl();
		final GridData tableLayout = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		tableLayout.widthHint = 600;
		tableLayout.heightHint = 300;
		tableControl.setLayoutData(tableLayout);

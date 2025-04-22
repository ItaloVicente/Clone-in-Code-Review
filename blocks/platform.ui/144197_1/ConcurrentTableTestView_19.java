		table = new TableViewer(parent, SWT.VIRTUAL);
		contentProvider = new DeferredContentProvider(comparator);
		table.setContentProvider(contentProvider);

		GridData data = new GridData(GridData.FILL_BOTH);
		table.getControl().setLayoutData(data);
		table.setInput(model);

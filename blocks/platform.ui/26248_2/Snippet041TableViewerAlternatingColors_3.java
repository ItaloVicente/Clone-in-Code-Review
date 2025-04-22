		final TableViewer viewer = new TableViewer(shell, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.VIRTUAL);

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		String[] labels = { "Column 1", "Column 2" };
		for (String label : labels) {
			createColumnFor(viewer, label);
		}
		viewer.setInput(createModel());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);

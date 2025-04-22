		final TableViewer viewer = new TableViewer(shell, SWT.BORDER
				| SWT.FULL_SELECTION);

		createColumnFor(viewer, "Column 1", 100);
		createColumnFor(viewer, "Column 2", 200);

		viewer.setLabelProvider(new LabelProvider());
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setCellModifier(new ICellModifier() {


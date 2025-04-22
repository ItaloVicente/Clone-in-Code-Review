		final TreeViewer viewer = new TreeViewer(shell, SWT.FULL_SELECTION);

		createColumnFor(viewer, "Column 1");
		createColumnFor(viewer, "Column 2");

		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(viewer.getTree()),
				new TextCellEditor(viewer.getTree()) });

		viewer.setColumnProperties(new String[] { "col1", "col2" });
		viewer.setCellModifier(new ICellModifier() {

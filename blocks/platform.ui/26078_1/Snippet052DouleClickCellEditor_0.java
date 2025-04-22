		int style = SWT.BORDER | SWT.FULL_SELECTION;
		final TableViewer viewer = new TableViewer(shell, style);
		viewer.setContentProvider(new MyContentProvider());
		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(viewer.getTable()),
				new TextCellEditor(viewer.getTable()),
				new TextCellEditor(viewer.getTable()) });
		viewer.setCellModifier(new ICellModifier() {

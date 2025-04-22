		final TableViewer v = new TableViewer(shell, SWT.BORDER
				| SWT.FULL_SELECTION);
		v.setContentProvider(new MyContentProvider());
		v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getTable()),
				new TextCellEditor(v.getTable()),
				new TextCellEditor(v.getTable()) });
		v.setCellModifier(new ICellModifier() {

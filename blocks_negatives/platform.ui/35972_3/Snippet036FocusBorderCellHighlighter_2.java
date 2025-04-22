	public Snippet036FocusBorderCellHighlighter(Shell shell) {
		final TableViewer v = new TableViewer(shell, SWT.BORDER
				| SWT.FULL_SELECTION);
		v.setLabelProvider(new MyLabelProvider());
		v.setContentProvider(ArrayContentProvider.getInstance());

		v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getTable()),
				new TextCellEditor(v.getTable()),
				new TextCellEditor(v.getTable()) });
		v.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}

			@Override
			public Object getValue(Object element, String property) {
				return "Column " + property + " => " + element.toString();
			}

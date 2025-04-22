				| ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);

		final TextCellEditor textCellEditor = new TextCellEditor(v.getTree());
		final BooleanCellEditor booleanCellEditor = new BooleanCellEditor(v.getTree());

		TreeViewerColumn column = new TreeViewerColumn(v, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Column 1");
		column.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return "Column 1 => " + element.toString();
			}

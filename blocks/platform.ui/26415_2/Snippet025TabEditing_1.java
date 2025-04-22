
		viewer.setColumnProperties(new String[] { "column1", "column2" });
		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(viewer.getTable()),
				new TextCellEditor(viewer.getTable()) });

		TableViewerEditor.create(viewer,
				new ColumnViewerEditorActivationStrategy(viewer),
				ColumnViewerEditor.TABBING_HORIZONTAL
						| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
						| ColumnViewerEditor.TABBING_VERTICAL);

		viewer.setInput(createModel());
		viewer.getTable().setLinesVisible(true);
	}

	private void createColumnFor(TableViewer viewer, String label, int width) {
		TableColumn tc = new TableColumn(viewer.getTable(), SWT.NONE);
		tc.setWidth(width);
		tc.setText(label);


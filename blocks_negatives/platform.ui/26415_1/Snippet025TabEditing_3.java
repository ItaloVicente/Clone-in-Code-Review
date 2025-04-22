		
		v.setColumnProperties(new String[] { "column1", "column2" });
		v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getTable()), new TextCellEditor(v.getTable()) });
		TableViewerEditor.create(v,new ColumnViewerEditorActivationStrategy(v),ColumnViewerEditor.TABBING_HORIZONTAL|ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR|ColumnViewerEditor.TABBING_VERTICAL);
		
		MyModel[] model = createModel();
		v.setInput(model);
		v.getTable().setLinesVisible(true);

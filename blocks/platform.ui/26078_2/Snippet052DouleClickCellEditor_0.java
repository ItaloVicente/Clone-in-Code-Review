		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(viewer.getTable()),
				new TextCellEditor(viewer.getTable()),
				new TextCellEditor(viewer.getTable()) });
		viewer.setCellModifier(new ICellModifier() {

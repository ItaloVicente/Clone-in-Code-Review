		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(viewer.getTree()) });
		viewer.setColumnProperties(new String[] { "name" });
		viewer.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}

			@Override
			public Object getValue(Object element, String property) {
				return ((MyModel) element).counter + "";
			}

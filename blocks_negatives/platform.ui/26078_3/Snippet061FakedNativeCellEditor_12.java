		column.setEditingSupport(new EditingSupport(v) {
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}

			@Override
			protected Object getValue(Object element) {
				return ((File) element).counter + "";
			}

			@Override
			protected void setValue(Object element, Object value) {
				((File) element).counter = Integer.parseInt(value.toString());
				v.update(element, null);
			}
		});

		column.setEditingSupport(new EditingSupport(v) {
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return booleanCellEditor;
			}

			@Override
			protected Object getValue(Object element) {
				return new Boolean(((File) element).execute);
			}

			@Override
			protected void setValue(Object element, Object value) {
				((File) element).execute = ((Boolean) value).booleanValue();
				v.update(element, null);
			}
		});

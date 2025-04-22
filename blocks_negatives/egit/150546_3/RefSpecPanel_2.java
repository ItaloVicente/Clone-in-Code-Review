		});
		column.setEditingSupport(new EditingSupport(tableViewer) {
			@Override
			protected boolean canEdit(final Object element) {
				return !isDeleteRefSpec(element);
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return forceUpdateCellEditor;
			}

		new TreeViewerColumn(tv, value)
				.setEditingSupport(new EditingSupport(tv) {

					protected void setValue(Object element, Object newValue) {
						Entry entry = (Entry) element;
						if (!entry.value.equals(newValue)) {
							entry.changeValue(newValue.toString());
							markDirty();
						}
					}

					protected Object getValue(Object element) {
						return ((Entry) element).value;
					}

					protected CellEditor getCellEditor(Object element) {
						return editor;
					}

					protected boolean canEdit(Object element) {
						return editable;
					}
				});

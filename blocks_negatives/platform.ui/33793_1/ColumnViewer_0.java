			viewer.setEditingSupport(new EditingSupport(this) {

				@Override
				public boolean canEdit(Object element) {
					Object[] properties = getColumnProperties();

					if (columnIndex < properties.length) {
						return getCellModifier().canModify(element,
								(String) getColumnProperties()[columnIndex]);
					}

					return false;
				}

				@Override
				public CellEditor getCellEditor(Object element) {
					CellEditor[] editors = getCellEditors();
					if (columnIndex < editors.length) {
						return getCellEditors()[columnIndex];
					}
					return null;
				}

				@Override
				public Object getValue(Object element) {
					Object[] properties = getColumnProperties();

					if (columnIndex < properties.length) {
						return getCellModifier().getValue(element,
								(String) getColumnProperties()[columnIndex]);
					}

					return null;
				}

				@Override
				public void setValue(Object element, Object value) {
					Object[] properties = getColumnProperties();

					if (columnIndex < properties.length) {
						getCellModifier().modify(findItem(element),
								(String) getColumnProperties()[columnIndex],
								value);
					}
				}

				@Override
				boolean isLegacySupport() {
					return true;
				}
			});

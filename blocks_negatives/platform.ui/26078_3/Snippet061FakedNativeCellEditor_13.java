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
				return new Boolean(((File) element).read);
			}

			@Override
			protected void setValue(Object element, Object value) {
				((File) element).read = ((Boolean) value).booleanValue();
				v.update(element, null);
			}
		});

		column = new TreeViewerColumn(v, SWT.CENTER);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Write");
		column.setLabelProvider(new EmulatedNativeCheckBoxLabelProvider(v) {

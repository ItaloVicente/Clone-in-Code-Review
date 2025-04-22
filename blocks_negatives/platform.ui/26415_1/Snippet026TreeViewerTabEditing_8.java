		});
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
				return ((MyModel) element).counter + "";
			}

			@Override
			protected void setValue(Object element, Object value) {
				((MyModel) element).counter = Integer
						.parseInt(value.toString());
				v.update(element, null);
			}
		});
		
		column = new TreeViewerColumn(v, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setMoveable(true);
		column.getColumn().setText("Column 3");
		column.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return "Column 3 => " + element.toString();
			}

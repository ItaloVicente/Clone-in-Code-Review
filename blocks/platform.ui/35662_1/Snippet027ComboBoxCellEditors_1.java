		TableViewerColumn viewerColumn = new TableViewerColumn(v, column);
		viewerColumn.setLabelProvider(new ColumnLabelProvider());
		viewerColumn.setEditingSupport(new EditingSupport(v) {

			@Override
			protected void setValue(Object element, Object value) {
				((MyModel) element).counter = ((Integer) value).intValue() * 10;
				getViewer().update(element, null);
			}

			@Override
			protected Object getValue(Object element) {
				return new Integer(((MyModel) element).counter / 10);
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new ComboBoxCellEditor(
						v.getTable(), new String[] { "Zero", "Ten", "Twenty", "Thirty",
							"Fourty", "Fifty", "Sixty", "Seventy", "Eighty",
							"Ninety" });
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});

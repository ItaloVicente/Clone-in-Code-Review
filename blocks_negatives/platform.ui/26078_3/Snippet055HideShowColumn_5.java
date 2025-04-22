		TreeViewerColumn column_1 = new TreeViewerColumn(v, SWT.NONE);
		column_1.getColumn().setWidth(200);
		column_1.getColumn().setMoveable(true);
		column_1.getColumn().setText("Column 1");
		column_1.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return "Column 1 => " + element.toString();
			}

		});
		column_1.setEditingSupport(new EditingSupport(v) {
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

		final TreeViewerColumn column_2 = new TreeViewerColumn(v, SWT.NONE);
		column_2.getColumn().setWidth(200);
		column_2.getColumn().setMoveable(true);
		column_2.getColumn().setText("Column 2");
		column_2.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return "Column 2 => " + element.toString();
			}

		});
		column_2.setEditingSupport(new EditingSupport(v) {
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

		TreeViewerColumn column_3 = new TreeViewerColumn(v, SWT.NONE);
		column_3.getColumn().setWidth(200);
		column_3.getColumn().setMoveable(true);
		column_3.getColumn().setText("Column 3");
		column_3.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return "Column 3 => " + element.toString();
			}

		});
		column_3.setEditingSupport(new EditingSupport(v) {
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

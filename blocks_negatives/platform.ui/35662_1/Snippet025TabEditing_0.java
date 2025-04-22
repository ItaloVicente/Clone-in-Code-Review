		viewer.setLabelProvider(new LabelProvider());
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return ((MyModel) element).counter % 2 == 0;
			}

			@Override
			public Object getValue(Object element, String property) {
				return ((MyModel) element).counter + "";
			}

			@Override
			public void modify(Object element, String property, Object value) {
				TableItem item = (TableItem) element;
				((MyModel) item.getData()).counter = Integer.parseInt(value
						.toString());
				viewer.update(item.getData(), null);
			}

		});

		viewer.setColumnProperties(new String[] { "column1", "column2" });
		viewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(viewer.getTable()),
				new TextCellEditor(viewer.getTable()) });


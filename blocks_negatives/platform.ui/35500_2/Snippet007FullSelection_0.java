		v.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}

			@Override
			public Object getValue(Object element, String property) {
				return ((MyModel)element).counter + "";
			}

			@Override
			public void modify(Object element, String property, Object value) {
				TableItem item = (TableItem)element;
				((MyModel)item.getData()).counter = Integer.parseInt(value.toString());
				v.update(item.getData(), null);
			}

		});
		v.setColumnProperties(new String[] { "column1", "column2" });
		v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getTable()),new TextCellEditor(v.getTable()) });

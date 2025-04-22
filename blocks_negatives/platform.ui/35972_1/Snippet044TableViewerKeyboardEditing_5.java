		v.setCellEditors(new CellEditor[] { new TextCellEditor(v.getTable()) });
		v.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return true;
			}

			@Override
			public Object getValue(Object element, String property) {
				return "Column " + property + " => " + element.toString();
			}

			@Override
			public void modify(Object element, String property, Object value) {

			}

		});

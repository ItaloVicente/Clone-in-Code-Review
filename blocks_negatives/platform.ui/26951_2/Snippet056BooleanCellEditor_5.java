			@Override
			protected CellEditor getCellEditor(Object element) {
				return textCellEditor;
			}

			@Override
			protected Object getValue(Object element) {
				return ((MyModel) element).counter + "";
			}

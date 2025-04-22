		protected CellEditor getCellEditor(Object element) {
			return new TextCellEditor((Composite) getViewer().getControl());
		}

		@Override
		protected boolean canEdit(Object element) {

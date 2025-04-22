		return event -> {
			switch (event.type) {
			case SWT.Paint:
				paint(event);
				break;

			case SWT.KeyDown:
				getParent().notifyListeners(SWT.KeyDown, event);
				ArrayList<Object> list = new ArrayList<>();
				for (ViewerCell cell : cells) {
					list.add(cell.getElement());
				}
				AbstractCellCursor.this.viewer.setSelection(new StructuredSelection(list));
				break;

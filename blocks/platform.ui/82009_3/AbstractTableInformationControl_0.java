
	public void next() {
		Table table = fTableViewer.getTable();
		if (fFilterText.isFocusControl()) {
			fTableViewer.getTable().setFocus();
			fTableViewer.getTable().setSelection(0);
		} else if (table.isFocusControl()) {
			int index = fTableViewer.getTable().getSelectionIndex();
			if (index == fTableViewer.getTable().getItemCount() - 1) {
				fFilterText.setFocus();
			} else {
				fTableViewer.getTable().setSelection(index + 1);
			}
		}
	}


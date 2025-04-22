	private void deleteSelectedItem() {
		int index = table.getSelectionIndex();
		if (index == -1 || index >= table.getItemCount()) {
			return;
		}
		TableItem item = table.getItem(index);
		close(item);
	}

	private void close(TableItem ti) {
		if (ti.getData() instanceof EditorReference) {
			int index = table.indexOf(ti);
			EditorReference ed = (EditorReference) ti.getData();
			page.closeEditors(new IEditorReference[] { ed }, false);
			table.setFocus();
			tableViewer.setInput(getInput(page));

			if (table.getItemCount() == 0) {
				cancel(dialog);
			}

			if (table.isDisposed()) {
				return;
			}

			if (index > 0 && index <= table.getItemCount()) {
				index -= 1;
			}
			table.setSelection(index);
		}
	}


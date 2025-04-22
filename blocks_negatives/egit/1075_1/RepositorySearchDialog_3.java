	private boolean hasCheckedItems() {
		for (TreeItem item : fTreeViewer.getTree().getItems())
			if (item.getChecked())
				return true;

		return false;
	}

	private void toggleSelection() {
		for (TreeItem item : fTreeViewer.getTree().getItems())
			item.setChecked(!item.getChecked());
		enableOk();
	}


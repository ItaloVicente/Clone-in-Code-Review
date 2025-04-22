		editorsTable.addDisposeListener(e -> {
			for (Iterator images1 = imageCache.values().iterator(); images1.hasNext();) {
				Image i1 = (Image) images1.next();
				i1.dispose();
			}
			for (Iterator images2 = disabledImageCache.values().iterator(); images2.hasNext();) {
				Image i2 = (Image) images2.next();
				i2.dispose();
			}
		});
		editorsTable.setFocus();
		applyDialogFont(dialogArea);
		return dialogArea;
	}

	private void updateButtons() {
		TableItem selectedItems[] = editorsTable.getSelection();
		boolean hasDirty = false;
		for (TableItem selectedItem : selectedItems) {
			Adapter editor = (Adapter) selectedItem.getData();
			if (editor.isDirty()) {
				hasDirty = true;
				break;
			}
		}
		saveSelected.setEnabled(hasDirty);

		TableItem allItems[] = editorsTable.getItems();
		boolean hasClean = false;
		for (TableItem tableItem : allItems) {
			Adapter editor = (Adapter) tableItem.getData();
			if (!editor.isDirty()) {
				hasClean = true;
				break;
			}
		}
		selectClean.setEnabled(hasClean);
		invertSelection.setEnabled(allItems.length > 0);
		closeSelected.setEnabled(selectedItems.length > 0);

		Button ok = getOkButton();
		if (ok != null) {

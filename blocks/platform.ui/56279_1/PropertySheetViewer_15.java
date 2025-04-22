		}
		return new StructuredSelection(entries);
	}

	private void handleSelect(TreeItem selection) {
		if (cellEditor != null) {
			applyEditorValue();
			deactivateCellEditor();
		}

		if (selection == null) {
			setMessage(null);
			setErrorMessage(null);
		} else {
			Object object = selection.getData();
			if (object instanceof IPropertySheetEntry) {
				IPropertySheetEntry activeEntry = (IPropertySheetEntry) object;

				setMessage(activeEntry.getDescription());

				activateCellEditor(selection);
			}
		}
		entrySelectionChanged();
	}

	private void handleTreeCollapse(TreeEvent event) {
		if (cellEditor != null) {
			applyEditorValue();
			deactivateCellEditor();
		}
	}

	private void handleTreeExpand(TreeEvent event) {
		createChildren(event.item);
	}

	void hideCategories() {
		isShowingCategories = false;
		categories = null;
		refresh();
	}

	void hideExpert() {
		isShowingExpertProperties = false;
		refresh();
	}

	private void hookControl() {
		tree.addSelectionListener(new SelectionAdapter() {
			@Override

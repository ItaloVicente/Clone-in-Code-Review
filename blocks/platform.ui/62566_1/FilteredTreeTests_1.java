		Dialog dialog = createFilteredTreeDialog(treeStyle);

		assertTreeViewerExists();
		assertNumberOfTopLevelItems(NUM_ITEMS);

		dialog.close();
	}

	private Dialog createFilteredTreeDialog() {
		return createFilteredTreeDialog(SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	private Dialog createFilteredTreeDialog(final int treeStyle) {

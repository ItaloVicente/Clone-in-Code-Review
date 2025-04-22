		Dialog dialog = createFilteredTreeDialog(treeStyle);

		Assert.isNotNull(fTreeViewer, "Filtered tree is null");
		assertNumberOfTopLevelItems(NUM_ITEMS);

		dialog.close();
	}

	private Dialog createFilteredTreeDialog() {
		return createFilteredTreeDialog(SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
	}

	private Dialog createFilteredTreeDialog(final int treeStyle) {

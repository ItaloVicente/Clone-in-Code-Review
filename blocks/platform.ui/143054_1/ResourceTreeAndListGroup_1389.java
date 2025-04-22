		tree.setLayoutData(data);
		tree.setFont(parent.getFont());

		treeViewer = new CheckboxTreeViewer(tree);
		treeViewer.setUseHashlookup(true);
		treeViewer.setContentProvider(treeContentProvider);
		treeViewer.setLabelProvider(treeLabelProvider);
		treeViewer.addTreeListener(treeListener);
		treeViewer.addCheckStateListener(checkListener);
		treeViewer.addSelectionChangedListener(selectionListener);
	}

	private boolean determineShouldBeAtLeastGrayChecked(Object treeElement) {
		List checked = (List) checkedStateStore.get(treeElement);
		if (checked != null && (!checked.isEmpty())) {

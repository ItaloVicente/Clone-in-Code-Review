		currentTreeSelection = selectedElement;
	}

	public void setAllSelections(final boolean selection) {

		BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
				() -> {
					setTreeChecked(root, selection);
					listViewer.setAllChecked(selection);

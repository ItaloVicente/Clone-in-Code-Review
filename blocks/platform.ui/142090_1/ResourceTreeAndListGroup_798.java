				}
			});
		}
	}

	private void populateListViewer(final Object treeElement) {
		listViewer.setInput(treeElement);

		if (!(expandedTreeNodes.contains(treeElement))
				&& whiteCheckedTreeItems.contains(treeElement)) {

			BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
					() -> {
						setListForWhiteSelection(treeElement);
						listViewer.setAllChecked(true);

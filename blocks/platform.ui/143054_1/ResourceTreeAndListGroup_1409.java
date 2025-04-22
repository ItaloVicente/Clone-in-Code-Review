			}
		}
	}

	public void setTreeProviders(ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
		List<?> items;
		if (root == null) {
			items = Collections.emptyList();
		} else {
			items = getAllWhiteCheckedItems();
			for (Object object : items) {
				setTreeChecked(object, false);
			}
		}

		treeViewer.setContentProvider(contentProvider);
		treeViewer.setLabelProvider(labelProvider);
		treeContentProvider = contentProvider;
		treeLabelProvider = labelProvider;

		for (Object object : items) {
			setTreeChecked(object, true);
		}
	}

	public void setTreeComparator(ViewerComparator comparator) {
		treeViewer.setComparator(comparator);
	}

	private void setWhiteChecked(Object treeElement, boolean isWhiteChecked) {
		if (isWhiteChecked) {
			if (!whiteCheckedTreeItems.contains(treeElement)) {

		List checked = (List) checkedStateStore.get(treeElement);
		if (checked != null && (!checked.isEmpty())) {
			Object[] listItems = listContentProvider.getElements(treeElement);
			if (listItems.length != checked.size())
				return false;
		}
		Object[] children = treeContentProvider.getChildren(treeElement);
		for (int i = 0; i < children.length; ++i) {
			if (!whiteCheckedTreeItems.contains(children[i])) {
				if (!treeViewer.getGrayed(children[i]))
					return false;
				if (!isEveryChildrenChecked(children[i]))
					return false;
			}

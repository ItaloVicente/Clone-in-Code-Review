	}

	public void initialCheckListItem(Object element) {
		Object parent = treeContentProvider.getParent(element);
		currentTreeSelection = parent;
		listItemChecked(element, true, false);
		updateHierarchy(parent);
	}

	public void initialCheckTreeItem(Object element) {
		treeItemChecked(element, true);
	}

	protected void initialize() {
		treeViewer.setInput(root);
	}

	protected void listItemChecked(Object listElement, boolean state,
			boolean updatingFromSelection) {
		List checkedListItems = (List) checkedStateStore
				.get(currentTreeSelection);

		if (state) {
			if (checkedListItems == null) {
				grayCheckHierarchy(currentTreeSelection);
				checkedListItems = (List) checkedStateStore
						.get(currentTreeSelection);
			}
			checkedListItems.add(listElement);
		} else {
			checkedListItems.remove(listElement);
			if (checkedListItems.isEmpty()) {
				ungrayCheckHierarchy(currentTreeSelection);
			}
		}

		if (updatingFromSelection) {

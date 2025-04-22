		Object parent = treeContentProvider.getParent(treeElement);
		if (parent != null) {
			grayUpdateHierarchy(parent);
		}
	}

	public void initialCheckListItem(Object element) {
		Object parent = treeContentProvider.getParent(element);
		selectAndReveal(parent);
		listViewer.setChecked(element, true);
		listItemChecked(element, true, false);
		grayUpdateHierarchy(parent);
	}

	public void initialCheckTreeItem(Object element) {
		treeItemChecked(element, true);
		selectAndReveal(element);
	}

	private void selectAndReveal(Object treeElement) {
		treeViewer.reveal(treeElement);
		IStructuredSelection selection = new StructuredSelection(treeElement);
		treeViewer.setSelection(selection);
	}

	private void initialize() {
		treeViewer.setInput(root);
		this.expandedTreeNodes = new HashSet<>();
		this.expandedTreeNodes.add(root);

	}

	private void listItemChecked(Object listElement, boolean state, boolean updatingFromSelection) {
		List checkedListItems = (List) checkedStateStore.get(currentTreeSelection);
		if (!expandedTreeNodes.contains(currentTreeSelection)) {

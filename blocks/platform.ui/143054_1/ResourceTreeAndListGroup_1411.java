	}

	public void updateSelections(Map items) {
		this.listViewer.setAllChecked(false);
		this.treeViewer.setCheckedElements(new Object[0]);
		this.whiteCheckedTreeItems = new HashSet();
		Set selectedNodes = new HashSet();
		checkedStateStore = new HashMap();


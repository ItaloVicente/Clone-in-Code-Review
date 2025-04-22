	private DisplayItem createToolBarStructure(MTrimBar toolbar) {
		DisplayItem root = new DisplayItem(null, null); // Create a
		createToolbarEntries(toolbar, root);
		return root;
	}



	public void setTreeArrowsMode(String arrowsMode) {
		Tree tree = getTree();
		if (arrowsMode == null) {
			tree.setData(TREE_ARROWS_MODE, null);
			setPaintListener(getTreeArrowsForegroundColor());
			return;
		}
		Assert.isTrue(TREE_ARROWS_MODE_TRIANGLE.equals(arrowsMode) || TREE_ARROWS_MODE_SQUARE.equals(arrowsMode));
		tree.setData(TREE_ARROWS_MODE, arrowsMode);
	}

	public String getTreeArrowsMode() {
		Tree tree = getTree();
		Object data = tree.getData(TREE_ARROWS_MODE);
		if (TREE_ARROWS_MODE_TRIANGLE.equals(data) || TREE_ARROWS_MODE_SQUARE.equals(data)) {
			return (String) data;
		}
		return TREE_ARROWS_MODE_TRIANGLE;
	}

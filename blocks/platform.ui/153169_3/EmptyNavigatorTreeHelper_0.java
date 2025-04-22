	private Tree tree;
	private final DisposeListener treeRootsDisposeListener = e ->
			e.widget.getDisplay().asyncExec(EmptyNavigatorTreeHelper.this::processRootsItems);

	private void processRootsItems() {
		if (tree.isDisposed()) {
			return;
		}
		TreeItem[] treeRoots = Arrays.stream(tree.getItems()).filter(item -> !item.isDisposed())
				.toArray(TreeItem[]::new);
		for (TreeItem treeRoot : treeRoots) {
			treeRoot.addDisposeListener(treeRootsDisposeListener);
		}
		if (switchTopControl()) {
			displayArea.requestLayout();
		}
	}

	public void setTree(Tree tree) {
		this.tree = tree;
		tree.addDisposeListener(treeRootsDisposeListener);
		parent.addPaintListener(e -> processRootsItems());
	}

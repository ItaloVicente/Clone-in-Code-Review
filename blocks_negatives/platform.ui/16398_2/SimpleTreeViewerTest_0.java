
	public void test327004() {
		treeViewer.setInput(null);
		treeViewer.setContentProvider(new TreeNodeContentProvider());

		final TreeNode[] children= new TreeNode[5];
		children[0]= new TreeNode("0");
		children[1]= new TreeNode("1");
		children[2]= new TreeNode("1");
		children[3]= new TreeNode("1");
		children[4]= new TreeNode("1");
		treeViewer.setInput(children);

		ViewerFilter filter= new ViewerFilter() {
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (element == children[0] || element == children[1] || element == children[2] || element == children[4])
					return false;
				return true;
			}
		};
		treeViewer.setFilters(new ViewerFilter[] { filter });
		int i= treeViewer.getTree().getItemCount();

		assertEquals(4, i); // 4 because the filter doesn't work due to equal nodes
	}


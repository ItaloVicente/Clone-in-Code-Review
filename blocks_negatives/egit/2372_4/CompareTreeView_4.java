	private void buildTrees() {
		final Object[] wsExpaneded = leftTree.getExpandedElements();
		final Object[] gitExpanded = rightTree.getExpandedElements();
		final ISelection wsSel = leftTree.getSelection();
		final ISelection gitSel = rightTree.getSelection();
		rightTree.setInput(null);
		leftTree.setInput(null);

		if (leftVersion == null) {
			leftTree
					.setContentProvider(new LocalWorkbenchTreeContentProvider());
			leftTree.setLabelProvider(new AddingWorkbenchLabelProvider());

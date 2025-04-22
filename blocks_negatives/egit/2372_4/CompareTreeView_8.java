	private void reactOnSelection(SelectionChangedEvent event) {
		if (rightTree.getTree().isFocusControl()
				&& !(leftTree.getSelection().equals(event.getSelection())))
			leftTree.setSelection(event.getSelection());
		else if (leftTree.getTree().isFocusControl()
				&& !(rightTree.getSelection().equals(event.getSelection())))
			rightTree.setSelection(event.getSelection());
	}

	private void reactOnCollapse(TreeExpansionEvent event) {
		if (event.getSource() == rightTree)
			leftTree.collapseToLevel(event.getElement(), 1);
		else if (event.getSource() == leftTree)
			rightTree.collapseToLevel(event.getElement(), 1);
	}

	private void reactOnExpand(TreeExpansionEvent event) {
		if (event.getSource() == rightTree)
			leftTree.expandToLevel(event.getElement(), 1);
		else if (event.getSource() == leftTree)
			rightTree.expandToLevel(event.getElement(), 1);
	}


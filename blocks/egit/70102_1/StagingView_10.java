		Tree tree = unstagedViewer.getTree();
		if (tree.getItemCount() > 0) {
			tree.setSelection(tree.getItems());
			unstagedViewer.getControl().setFocus();
			return;
		}
		commitMessageText.setFocus();

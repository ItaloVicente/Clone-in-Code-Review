
		if (RebaseInteractivePreferences.isOrderReversed()) {
			Tree tree = planTreeViewer.getTree();
			TreeItem bottomItem = tree.getItem(tree.getItemCount() - 1);
			tree.showItem(bottomItem);
		}

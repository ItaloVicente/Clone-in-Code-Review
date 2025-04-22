		TestElement first = fRootElement.getFirstChild();
		TreeItem ti = (TreeItem) fViewer.testFindItem(first);
		Tree tree = ti.getParent();
		return tree.getItemCount();
	}

	@Override

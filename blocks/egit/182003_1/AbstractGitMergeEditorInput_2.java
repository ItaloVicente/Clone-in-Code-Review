	private void collapse(DiffContainer top) {
		IDiffElement[] children = top.getChildren();
		boolean isRoot = top.getParent() == null;
		if (!isRoot) {
			while (children != null && children.length == 1) {
				IDiffElement singleChild = children[0];
				if (singleChild instanceof FolderNode) {
					FolderNode node = (FolderNode) singleChild;
					top.remove(singleChild);
					top.getParent().add(singleChild);
					node.setName(top.getName() + '/' + singleChild.getName());
					((DiffContainer) top.getParent()).remove(top);
					children = node.getChildren();
					top = node;
				} else {
					return;
				}

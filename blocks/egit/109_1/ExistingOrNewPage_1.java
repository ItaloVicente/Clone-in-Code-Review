		for (int i = 0; i < ret.length; ++i) {
			TreeItem treeItem = tree.getSelection()[i];
			while (treeItem.getData() ==  null && treeItem.getParentItem() != null) {
				treeItem = treeItem.getParentItem();
			}
			ret[i] = (IProject)treeItem.getData();
		}

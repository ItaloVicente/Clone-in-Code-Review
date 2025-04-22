			public void widgetSelected(SelectionEvent e) {
				TreeItem t = (TreeItem) e.item;
				for(TreeItem ti : t.getItems())
					tree.deselect(ti);
				if (t.getParentItem() != null) {
					tree.deselect(t.getParentItem());
					for(TreeItem ti : t.getParentItem().getItems())
						if (ti != t)
							tree.deselect(ti);
				}
				Set<IProject> projects = new HashSet<IProject>();
				for (TreeItem treeItem : tree.getSelection()) {
					if (treeItem.getData() ==  null && treeItem.getParentItem() != null) {
						treeItem = treeItem.getParentItem();

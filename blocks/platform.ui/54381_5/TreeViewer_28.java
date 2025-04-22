			treeControl.addListener(SWT.SetData, event -> {
				if (contentProviderIsLazy) {
					TreeItem item = (TreeItem) event.item;
					TreeItem parentItem = item.getParentItem();
					int index = event.index;
					virtualLazyUpdateWidget(
							parentItem == null ? (Widget) getTree()
									: parentItem, index);

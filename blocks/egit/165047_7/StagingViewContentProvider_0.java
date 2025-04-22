
		Tree tree = treeViewer.getTree();
		tree.addListener(SWT.SetData, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem item = (TreeItem) event.item;
				TreeItem parentItem = item.getParentItem();
				Object parent = parentItem == null ? null
						: parentItem.getData();
				Object[] children = getChildren(parent);
				if (event.index < children.length) {
					ILabelProvider labelProvider = (ILabelProvider) treeViewer
						.getLabelProvider();
					Object entry = children[event.index];
					item.setImage(labelProvider.getImage(entry));
					item.setText(labelProvider.getText(entry));
					item.setData(entry);
				}
			}
		});

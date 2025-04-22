		tree.addListener(SWT.SetData, new Listener() {

			@Override
			public void handleEvent(Event event) {
				StagingViewContentProvider contentProvider = getContentProvider(
						treeViewer);
				StagingEntry[] content = contentProvider.getStagingEntries();
				StagingEntry entry = content[event.index];
				TreeItem item = (TreeItem) event.item;
				ILabelProvider labelProvider = (ILabelProvider) treeViewer
						.getLabelProvider();
				item.setImage(labelProvider.getImage(entry));
				item.setText(labelProvider.getText(entry));
			}
		});

		tv.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.isEmpty()) {
					return;
				}

				Object element = selection.getFirstElement();
				ITreeContentProvider contentProvider = (ITreeContentProvider) tv
						.getContentProvider();
				if (contentProvider.hasChildren(element)) {
					tv.setExpandedState(element, !tv.getExpandedState(element));
				} else {
					Object[] selectionArray = selection.toArray();
					for (Object selectedElement : selectionArray) {
						RepositoryTreeNode node = (RepositoryTreeNode) selectedElement;
						if (node.getType() != RepositoryTreeNodeType.FILE) {
							return;
						}
					}

					for (Object selectedElement : selectionArray) {
						RepositoryTreeNode node = (RepositoryTreeNode) selectedElement;
						openFile((File) node.getObject());
					}
				}
			}
		});

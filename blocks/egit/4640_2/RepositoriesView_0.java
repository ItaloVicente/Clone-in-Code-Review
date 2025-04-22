		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection sel = (TreeSelection) event.getSelection();
				RepositoryTreeNode element = (RepositoryTreeNode) sel
						.getFirstElement();
				if (element instanceof RefNode || element instanceof TagNode) {
					executeOpenCommand();
				}
			}
		});

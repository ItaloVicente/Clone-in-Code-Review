		Runnable addAndRemove = () -> {
			if (viewer instanceof AbstractTreeViewer) {
				AbstractTreeViewer treeViewer = (AbstractTreeViewer) viewer;
				if (hasRename) {
					treeViewer.getControl().setRedraw(false);
				}
				try {
					if (addedObjects.length > 0) {
						treeViewer.add(resource, addedObjects);

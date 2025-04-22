		Runnable addAndRemove = new Runnable(){
			@Override
			public void run() {
				if (viewer instanceof AbstractTreeViewer) {
					AbstractTreeViewer treeViewer = (AbstractTreeViewer) viewer;
					if (hasRename) {
						treeViewer.getControl().setRedraw(false);

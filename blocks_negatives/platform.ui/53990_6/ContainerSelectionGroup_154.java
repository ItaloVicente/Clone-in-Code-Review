		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection) selection)
							.getFirstElement();
					if (item == null) {
						return;
					}
					if (treeViewer.getExpandedState(item)) {
						treeViewer.collapseToLevel(item, 1);
					} else {
						treeViewer.expandToLevel(item, 1);
					}

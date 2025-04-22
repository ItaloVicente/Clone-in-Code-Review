			final RepositoryTreeNode nodeToShow = selectedNodes.get(0);
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						IHistoryView part = (IHistoryView) PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(IHistoryView.VIEW_ID);

	private static void addListenerToDisableAutoExpandOnCollapse(
			TreeViewer treeViewer) {
		treeViewer.addTreeListener(new ITreeViewerListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
				disableAutoExpand(event.getTreeViewer());
			}

			public void treeExpanded(TreeExpansionEvent event) {
			}
		});
	}

	private static void enableAutoExpand(AbstractTreeViewer treeViewer) {
		treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);
	}

	private static void disableAutoExpand(AbstractTreeViewer treeViewer) {
		treeViewer.setAutoExpandLevel(0);
	}


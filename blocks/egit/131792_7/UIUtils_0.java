
	public static void expandAll(AbstractTreeViewer viewer) {
		viewer.getControl().setRedraw(false);
		try {
			viewer.expandAll();
		} finally {
			viewer.getControl().setRedraw(true);
		}
	}

	public static void collapseAll(AbstractTreeViewer viewer) {
		viewer.getControl().setRedraw(false);
		try {
			viewer.collapseAll();
		} finally {
			viewer.getControl().setRedraw(true);
		}
	}

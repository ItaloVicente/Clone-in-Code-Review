	private void setPaintListener(Color color) {
		if (Constants.OS_WIN32.equals(Platform.getOS())) {
			Tree tree = getTree();

			tree.removeListener(SWT.PaintItem, treeItemSquaresPaintListener);
			tree.removeListener(SWT.PaintItem, treeItemArrowsPaintListener);

			if (color != null) {
				String treeArrowsMode = getTreeArrowsMode();
				if (TREE_ARROWS_MODE_TRIANGLE.equals(treeArrowsMode)) {
					tree.addListener(SWT.PaintItem, treeItemArrowsPaintListener);

				} else if (TREE_ARROWS_MODE_SQUARE.equals(treeArrowsMode)) {
					tree.addListener(SWT.PaintItem, treeItemSquaresPaintListener);

				} else {
					throw new AssertionError("Should not get here"); //$NON-NLS-1$
				}
			}
		}
	}


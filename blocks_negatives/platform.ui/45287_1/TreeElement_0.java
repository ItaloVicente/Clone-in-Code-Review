
		if (Constants.OS_WIN32.equals(Platform.getOS())) {

			tree.removeListener(SWT.PaintItem, treeItemPaintListener);
			if (color != null) {
				tree.addListener(SWT.PaintItem, treeItemPaintListener);
			}
		}

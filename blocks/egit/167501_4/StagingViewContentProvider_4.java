	public void refreshView() {
		if (!haveVirtualTree()) {
			treeViewer.refresh();
			return;
		}

		internalRedraw();
	}


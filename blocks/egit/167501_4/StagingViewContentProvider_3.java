
		if (!haveVirtualTree()) {
			return;
		}

		treeViewer.getTree().setData(newInput);
		internalRedraw();

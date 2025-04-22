
		if ((treeViewer.getControl().getStyle() & SWT.VIRTUAL) == 0) {
			return;
		}

		treeViewer.getTree().setData(newInput);
		internalRedraw();

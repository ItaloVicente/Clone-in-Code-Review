		getDisplay().asyncExec(() -> {
			if (!isDisposed()) {
				fFilteredTree.getViewer().refresh();
				fFilteredTree.setEnabled(true);
			}
		});

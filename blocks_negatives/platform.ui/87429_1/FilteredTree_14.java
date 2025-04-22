		filterText.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (quickSelectionMode) {
					return;
				}
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					updateTreeSelection(true);
				}

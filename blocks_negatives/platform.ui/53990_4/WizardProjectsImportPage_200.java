		archivePathField.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					updateProjectsList(archivePathField.getText().trim());
				}

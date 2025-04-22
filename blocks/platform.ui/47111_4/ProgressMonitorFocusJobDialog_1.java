		shell.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE) {
					cancelPressed();
					e.detail = SWT.TRAVERSE_NONE;
					e.doit = true;
				}
			}
		});

			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ESCAPE) {
					e.detail = SWT.TRAVERSE_NONE;
					cancelPressed();
				}
			}
		});

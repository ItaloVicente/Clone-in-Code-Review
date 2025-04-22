		listLabel.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_MNEMONIC && e.doit) {
					e.detail = SWT.TRAVERSE_NONE;
					list.getTable().setFocus();
				}

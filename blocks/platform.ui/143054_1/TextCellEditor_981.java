				checkSelection(); // see explanation below
				checkDeleteable();
				checkSelectable();
			}
		});
		text.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_ESCAPE
					|| e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
			}

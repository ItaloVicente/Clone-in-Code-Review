	private void redrawChildren() {
		for (Control child : parent.getChildren()) {
			if (child instanceof Composite) {
				child.reskin(SWT.ALL);
			}
		}
	}


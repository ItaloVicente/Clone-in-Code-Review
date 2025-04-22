	private void updateSize() {
		Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		size.x += IDialogConstants.HORIZONTAL_SPACING;
		size.y += IDialogConstants.VERTICAL_SPACING;

		scrolled.setMinSize(size);
	}


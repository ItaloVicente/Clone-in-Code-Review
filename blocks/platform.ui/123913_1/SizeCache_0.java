	private static boolean isWrapControl(Control control) {
		boolean wrapping = (control.getStyle() & SWT.WRAP) != 0;

		if (control instanceof Link) {
			wrapping = true;
		}
		return wrapping;
	}


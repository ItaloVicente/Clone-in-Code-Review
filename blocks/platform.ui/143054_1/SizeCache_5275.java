	private static boolean isPreferredWidthMaximum(Control control) {
		return (control instanceof ToolBar
		|| control instanceof Label);
	}

	private static boolean isWrapControl(Control control) {
		boolean wrapping = (control.getStyle() & SWT.WRAP) != 0;

		if (control instanceof Link) {
			wrapping = true;
		}
		return wrapping;
	}

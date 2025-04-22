	public static boolean isWrapControl(Control c) {
		if ((c.getStyle() & SWT.WRAP) != 0)
			return true;
		if (c instanceof Composite) {
			return ((Composite) c).getLayout() instanceof ILayoutExtension;
		}
		return false;
	}

	public static int getWidthHint(int wHint, Control c) {
		boolean wrap = isWrapControl(c);
		return wrap ? wHint : SWT.DEFAULT;
	}


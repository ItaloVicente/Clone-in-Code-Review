	public static int computeMinimumWidth(Control c, boolean changed) {
		if (c instanceof Composite) {
			Layout layout = ((Composite) c).getLayout();
			if (layout instanceof ILayoutExtension)
				return ((ILayoutExtension) layout).computeMinimumWidth(
						(Composite) c, changed);
		}
		return c.computeSize(FormUtil.getWidthHint(5, c), SWT.DEFAULT, changed).x;
	}

	public static int computeMaximumWidth(Control c, boolean changed) {
		if (c instanceof Composite) {
			Layout layout = ((Composite) c).getLayout();
			if (layout instanceof ILayoutExtension)
				return ((ILayoutExtension) layout).computeMaximumWidth(
						(Composite) c, changed);
		}
		return c.computeSize(SWT.DEFAULT, SWT.DEFAULT, changed).x;
	}


	private static int preventBug499850(int style) {
		if (((style & (SWT.VIRTUAL | SWT.MULTI)) == (SWT.VIRTUAL | SWT.MULTI))
			return style & ~SWT.MULTI;
		}
		return style;
	}


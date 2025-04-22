
	static final int INTERESTING_MARK_TREE_FILTER_INDEX = 0;

	private static int preventBug499850(int style) {
		if (((style & (SWT.VIRTUAL | SWT.MULTI)) == (SWT.VIRTUAL | SWT.MULTI))
				&& "gtk".equals(SWT.getPlatform()) && SWT.getVersion() < 4919) { //$NON-NLS-1$
			return style & ~SWT.MULTI;
		}
		return style;
	}


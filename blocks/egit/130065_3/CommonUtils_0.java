
	public static int getCommitIdColumnWidth(Control control,
			String columnHeading) {
		GC gc = new GC(control.getDisplay());
		try {
			gc.setFont(control.getFont());
			return Math.max(gc.stringExtent("bbbbbbb").x, //$NON-NLS-1$
					gc.stringExtent(columnHeading).x) + 2 * TABLE_INSET;
		} finally {
			gc.dispose();
		}
	}

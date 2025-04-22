	public static void storeDefaultCursor(Control control) {
		if (control.getData(DEFAULT_CURSOR) == null) {
			control.setData(DEFAULT_CURSOR, control.getCursor());
		}
	}

	public static void restoreDefaultCursor(Control control) {
		Cursor defaultCursor = (Cursor) control.getData(DEFAULT_CURSOR);
		if (defaultCursor != null) {
			control.setCursor(defaultCursor.isDisposed() ? control.getDisplay()
					.getSystemCursor(SWT.ARROW) : defaultCursor);
		}
	}


	}

	private boolean createSeparator(Menu menu, boolean needSeparator) {
		if (needSeparator) {
			new MenuItem(menu, SWT.SEPARATOR);
			return false;
		}
		return true;

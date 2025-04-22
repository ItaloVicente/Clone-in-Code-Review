		expandToLevel(level, false);
	}

	public void expandToLevel(int level, boolean disableRedraw) {
		BusyIndicator.showWhile(getControl().getDisplay(), () -> {
			expandToLevel(getRoot(), level);
		});

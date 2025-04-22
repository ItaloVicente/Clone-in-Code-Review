	public static boolean contextMenuItemExists(final AbstractSWTBot<?> bot,
			final String... texts) {
		final MenuItem menuItem = UIThreadRunnable
				.syncExec(new WidgetResult<MenuItem>() {
					public MenuItem run() {
						return getMenuItem(bot, texts);
					}
				});
		return menuItem != null;
	}


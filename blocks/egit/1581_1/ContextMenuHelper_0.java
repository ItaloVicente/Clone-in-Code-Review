	private static MenuItem getMenuItem(final AbstractSWTBot<?> bot,
			final String... texts) {
		MenuItem theItem = null;
		Control control = (Control) bot.widget;
		control.notifyListeners(SWT.MenuDetect, new Event());
		Menu menu = control.getMenu();
		for (String text : texts) {
			Matcher<?> matcher = allOf(instanceOf(MenuItem.class),
					withMnemonic(text));
			theItem = show(menu, matcher);
			if (theItem != null) {
				menu = theItem.getMenu();
			} else {
				hide(menu);
				break;
			}
		}
		return theItem;
	}
	
	public static boolean isContextMenuItemEnabled(final AbstractSWTBot<?> bot,
			final String... texts) {

		final AtomicBoolean enabled = new AtomicBoolean(false);
		final MenuItem menuItem = UIThreadRunnable
				.syncExec(new WidgetResult<MenuItem>() {
					@SuppressWarnings("unchecked")
					public MenuItem run() {
						MenuItem theItem = getMenuItem(bot, texts);
						if (theItem != null && theItem.isEnabled())
							enabled.set(true);
						return theItem;
					}
				});
		if (menuItem == null) {
			throw new WidgetNotFoundException("Could not find menu: "
					+ Arrays.asList(texts));
		}
		UIThreadRunnable.syncExec(new VoidResult() {
			public void run() {
				hide(menuItem.getParent());
			}
		});
		return enabled.get();
	}

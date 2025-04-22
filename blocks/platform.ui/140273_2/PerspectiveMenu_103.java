		final MenuManager manager = new MenuManager();
		fillMenu(manager);
		final IContributionItem items[] = manager.getItems();
		if (items.length == 0) {
			MenuItem item = new MenuItem(menu, SWT.NONE, index++);
			item.setText(NO_TARGETS_MSG);
			item.setEnabled(false);
		} else {
			for (IContributionItem item : items) {
				item.fill(menu, index++);
			}
		}
		dirty = false;
	}

	private final void fillMenu(final MenuManager manager) {
		manager.removeAll();


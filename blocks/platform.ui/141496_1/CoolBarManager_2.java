		if (itemToAdd == null) {
			return true;
		}

		String firstId = itemToAdd.getId();
		if (firstId == null) {
			return true;
		}

		IContributionItem[] currentItems = getItems();
		for (IContributionItem currentItem : currentItems) {
			if (currentItem == null) {
				continue;
			}

			String secondId = currentItem.getId();
			if (firstId.equals(secondId)) {
				if (Policy.TRACE_TOOLBAR) {
					System.out.println("Trying to add a duplicate item."); //$NON-NLS-1$
					new Exception().printStackTrace(System.out);
					System.out.println("DONE --------------------------"); //$NON-NLS-1$
				}
				return false;
			}
		}

		return true;
	}

	private void collapseSeparators(ListIterator<IContributionItem> iterator) {

		while (iterator.hasNext()) {
			IContributionItem item = iterator.next();
			if (!item.isSeparator()) {
				iterator.previous();
				return;
			}
		}
	}

	private boolean coolBarExist() {
		return coolBar != null && !coolBar.isDisposed();
	}

	public CoolBar createControl(Composite parent) {
		Assert.isNotNull(parent);
		if (!coolBarExist()) {
			coolBar = new CoolBar(parent, itemStyle);
			coolBar.setMenu(getContextMenuControl());
			coolBar.setLocked(false);
			update(false);
		}
		return coolBar;
	}

	public void dispose() {
		if (coolBarExist()) {
			coolBar.dispose();
			coolBar = null;
		}
		IContributionItem[] items = getItems();
		for (IContributionItem item : items) {
			item.dispose();
		}
		if (contextMenuManager != null) {
			contextMenuManager.dispose();
			contextMenuManager = null;
		}

	}

	private void dispose(CoolItem item) {
		if ((item != null) && !item.isDisposed()) {

			item.setData(null);
			Control control = item.getControl();
			if ((control != null) && !control.isDisposed()) {
				item.setControl(null);
				control.dispose();
			}
			item.dispose();
		}
	}

	private CoolItem findCoolItem(IContributionItem item) {
		CoolItem[] coolItems = (coolBar == null) ? null : coolBar.getItems();
		return findCoolItem(coolItems, item);
	}

	private CoolItem findCoolItem(CoolItem[] items, IContributionItem item) {
		if (items == null) {

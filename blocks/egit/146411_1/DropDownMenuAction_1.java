	private Menu fillMenu(Menu m) {
		for (IAction action : getActions()) {
			ActionContributionItem item = new ActionContributionItem(action);
			item.fill(m, -1);
		}
		return m;
	}

	private Menu dispose(Menu m) {
		if (m != null) {
			if (!m.isDisposed()) {
				m.dispose();
			}
		}
		return null;
	}


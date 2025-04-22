	private void updateToolbar() {
		if (toolbar != null) {
			for (IContributionItem item : toolbar.getItems()) {
				item.isEnabled();
			}
			toolbar.update(true);
		}
	}


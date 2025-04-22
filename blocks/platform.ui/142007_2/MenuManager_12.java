	protected void update(boolean force, boolean recursive) {
		if (isDirty() || force) {
			if (menuExist()) {
				IContributionItem[] items = getItems();
				List<IContributionItem> clean = new ArrayList<>(items.length);
				IContributionItem separator = null;
				for (IContributionItem item : items) {
					IContributionItem ci = item;
					if (!isChildVisible(ci)) {

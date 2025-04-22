		int oldCount = toolBar.getItemCount();

		IContributionItem[] items = getItems();
		ArrayList<IContributionItem> clean = new ArrayList<IContributionItem>(items.length);
		IContributionItem separator = null;
		for (IContributionItem ci : items) {
			if (!isChildVisible(ci)) {
				continue;
			}
			if (ci.isSeparator()) {
				separator = ci;
			} else {
				if (separator != null) {
					if (clean.size() > 0) {
						clean.add(separator);

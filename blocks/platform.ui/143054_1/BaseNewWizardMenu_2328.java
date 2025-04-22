	protected void addItems(List list) {
		if (addShortcuts(list)) {
			list.add(new Separator());
		}
		list.add(new ActionContributionItem(getShowDialogAction()));
	}

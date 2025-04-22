		return contextMenuManager;
	}

	public CoolBar getControl() {
		return coolBar;
	}

	private ArrayList<IContributionItem> getItemList() {
		IContributionItem[] cbItems = getItems();
		ArrayList<IContributionItem> list = new ArrayList<>(cbItems.length);
		for (IContributionItem cbItem : cbItems) {
			list.add(cbItem);
		}
		return list;
	}

	@Override

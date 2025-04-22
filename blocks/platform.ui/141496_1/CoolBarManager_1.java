		Assert.isNotNull(toolBarManager);
		super.add(new ToolBarContributionItem(toolBarManager));
	}

	private ArrayList<IContributionItem> adjustContributionList(ArrayList<IContributionItem> contributionList) {
		IContributionItem item;
		if (contributionList.size() != 0) {
			item = contributionList.get(0);
			if (item.isSeparator()) {
				contributionList.remove(0);
			}

			ListIterator<IContributionItem> iterator = contributionList.listIterator();
			while (iterator.hasNext()) {
				item = iterator.next();
				if (item.isSeparator()) {
					while (iterator.hasNext()) {
						item = iterator.next();
						if (item.isSeparator()) {
							iterator.remove();
						} else {
							break;
						}
					}

				}
			}
			if (contributionList.size() != 0) {
				item = contributionList.get(contributionList.size() - 1);
				if (item.isSeparator()) {
					contributionList.remove(contributionList.size() - 1);
				}
			}
		}
		return contributionList;

	}

	@Override

	public static SWTBotTreeItem getNode(SWTBotTreeItem[] nodes, String searchText) {
		List<String> texts = new ArrayList<String>();
		List<SWTBotTreeItem> matchingItems = new ArrayList<SWTBotTreeItem>();

		for (SWTBotTreeItem item : nodes) {
			String text = item.getText();
			if (text.contains(searchText))
				matchingItems.add(item);
			texts.add(text);
		}

		if (matchingItems.isEmpty())
			throw new WidgetNotFoundException(
					"Tree item element containg text \"" + searchText
							+ "\" was not found. Existing tree items:\n"
							+ StringUtils.join(texts, "\n"));
		else if (matchingItems.size() > 1)
			throw new WidgetNotFoundException(
					"Tree item element containg text \""
							+ searchText
							+ "\" could not be uniquely identified. All tree items:\n"
							+ StringUtils.join(texts, "\n"));

		return matchingItems.get(0);
	}

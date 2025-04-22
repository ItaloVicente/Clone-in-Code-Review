		List<String> existingItems = new ArrayList<String>();
		for (SWTBotTreeItem item : items) {
			if (item.getText().contains(text))
				return item;
			existingItems.add(item.getText());
		}

		throw new WidgetNotFoundException(
				"Tree item element containing text \"" + text
						+ "\" was not found. Existing tree items:\n"
						+ StringUtils.join(existingItems, "\n"));

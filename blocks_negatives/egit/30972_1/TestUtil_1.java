		for (SWTBotTreeItem item : node.getItems()) {
			String itemText = item.getText();
			StringTokenizer tok = new StringTokenizer(itemText, " ");
			String name = tok.nextToken();
			if (name.equals(">"))
				name = tok.nextToken();
			if (childNodeText.equals(name)
					|| name.startsWith(childNodeText + " "))
				return item;
		}
		return null;

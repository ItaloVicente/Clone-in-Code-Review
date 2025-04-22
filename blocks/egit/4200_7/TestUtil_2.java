	
	public SWTBotTreeItem getChildNode(SWTBotTreeItem node, String childNodeText) {
		for (SWTBotTreeItem item : node.getItems()) {
			String itemText = item.getText();
			StringTokenizer tok = new StringTokenizer(itemText, " ");
			String name = tok.nextToken();
			if (name.equals(">"))
				name = tok.nextToken();
			if (childNodeText.equals(name))
				return item;
		}
		return null;
	}


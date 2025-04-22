		for (SWTBotTreeItem item : projectExplorerTree.getAllItems()) {
			String itemText = item.getText();
			StringTokenizer tok = new StringTokenizer(itemText, " ");
			String name = tok.nextToken();
			if (name.equals(">"))
				name = tok.nextToken();
			if (project.equals(name))
				return item;
		}
		return null;

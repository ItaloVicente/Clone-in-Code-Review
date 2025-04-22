	public void testExpandSymbolicRef() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		SWTBotTreeItem item = getSymbolicRefsItem(tree, repositoryFile)
				.expand();
		List<String> children = item.getNodes();
		boolean found = false;
		for (String child : children)
			if (child.contains(Constants.HEAD))
				found = true;
		assertTrue(found);

	public SWTBotTreeItem getProjectItem(SWTBotTree projectExplorerTree,
			String project) {
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
	}

	public static RevCommit getHeadCommit(Repository repository)
			throws Exception {
		RevCommit headCommit = null;
		ObjectId parentId = repository.resolve(Constants.HEAD);
		if (parentId != null)
			headCommit = new RevWalk(repository).parseCommit(parentId);
		return headCommit;
	}

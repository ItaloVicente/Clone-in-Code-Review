	private SWTBotTreeItem expandWorkingTreeNode(SWTBotTree syncViewTree) {
		String workingTreeNodeNameString = getWorkingTreeNodeName(syncViewTree);
		return syncViewTree.expandNode(workingTreeNodeNameString);
	}

	private String getWorkingTreeNodeName(SWTBotTree syncViewTree) {
		for (SWTBotTreeItem item : syncViewTree.getAllItems()) {
			String nodeName = item.getText();
			if (nodeName.contains(UIText.GitModelWorkingTree_workingTree))
				return nodeName;
		}

		return UIText.GitModelWorkingTree_workingTree;
	}


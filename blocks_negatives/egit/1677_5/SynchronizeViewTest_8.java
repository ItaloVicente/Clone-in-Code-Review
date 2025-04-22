		coreTreeItem.collapse();

		SWTBotTreeItem uiTreeItem = tree.getAllItems()[6];
		uiTreeItem.expand();
		for (String node : uiTreeItem.getNodes()) {
			if (node.contains("pom.xml")) {
				uiTreeItem.getNode(node).doubleClick();
				break;
			}
		}

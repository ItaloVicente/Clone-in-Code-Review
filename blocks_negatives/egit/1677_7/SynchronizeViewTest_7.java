		SWTBotTreeItem coreTreeItem = tree.getAllItems()[3];
		coreTreeItem.expand();
		for (String node : coreTreeItem.getNodes()) {
			if (node.contains("pom.xml")) {
				coreTreeItem.getNode(node).doubleClick();
				break;
			}
		}

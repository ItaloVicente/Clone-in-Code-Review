			explorerItem = projectItem;
		else if (path.length == 2) {
			explorerItem = testUtil.getChildNode(projectItem.expand(), path[1]);
		} else {
			SWTBotTreeItem childItem = testUtil.getChildNode(
					projectItem.expand(), path[1]);
			explorerItem = testUtil.getChildNode(childItem.expand(), path[2]);
		}

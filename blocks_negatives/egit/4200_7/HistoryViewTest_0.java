			explorerItem = getProjectItem(projectExplorerTree, path[0]);
		else if (path.length == 2)
			explorerItem = getProjectItem(projectExplorerTree, path[0])
					.expand().getNode(path[1]);
		else
			explorerItem = getProjectItem(projectExplorerTree, path[0])
					.expand().getNode(path[1]).expand().getNode(path[2]);

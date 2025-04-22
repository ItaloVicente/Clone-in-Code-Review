		if (filterSpec.getTreeDepthLimit() < 0 ||
			treeDepth <= filterSpec.getTreeDepthLimit()) {
			return false;
		}

		walker.skipTree();
		return true;

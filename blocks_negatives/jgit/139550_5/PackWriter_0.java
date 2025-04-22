

		if (type == OBJ_BLOB) {
			treeDepth++;
		}

		if (filterSpec.getTreeDepthLimit() >= 0 &&
			treeDepth > filterSpec.getTreeDepthLimit()) {
			return;
		}

		addObject(src, type, pathHashCode);

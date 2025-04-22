	private boolean depthSkip(@NonNull RevObject obj
			@NonNull Set<? extends AnyObjectId> want
		long treeDepth = walker.getTreeDepth();
		int type = obj.getType();

		if (type == OBJ_BLOB) {
			treeDepth++;
		} else {
			stats.treesTraversed++;
		}

		if (filterSpec.getTreeDepthLimit() < 0 ||
			treeDepth <= filterSpec.getTreeDepthLimit() ||
			want.contains(obj)) {
			return false;
		}

		walker.skipTree();
		return true;
	}


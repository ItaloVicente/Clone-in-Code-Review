	private boolean depthSkip(@NonNull RevObject obj
		long treeDepth = walker.getTreeDepth();


		if (obj.getType() == OBJ_BLOB) {
			treeDepth++;
		}

		return filterSpec.getTreeDepthLimit() != -1 &&
				treeDepth > filterSpec.getTreeDepthLimit();
	}


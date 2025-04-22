		ObjectModeCache.CachedObject cached = objectModeCache.get(tree
				TYPE_TREE);

		if (cached.hasSetChildren()) {
			for (ObjectModeCache.CachedObject child : cached
					.getChildren()) {
				switch (child.mode) {
				case TYPE_FILE:
				case TYPE_SYMLINK:
					lookupBlob(child).flags |= UNINTERESTING;
					break;

				case TYPE_TREE:
					markTreeUninterestingRec(lookupTree(child));
					break;

				case TYPE_GITLINK:
					break;
				}
			}
			return;
		}

		ObjectModeCache.ChildList children = objectModeCache.getChildList();

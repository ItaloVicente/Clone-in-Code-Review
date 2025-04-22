				switch (treeWalk.getRawMode(0) & FileMode.TYPE_MASK) {
				case FileMode.TYPE_MISSING:
				case FileMode.TYPE_GITLINK:
					continue;
				case FileMode.TYPE_TREE:
				case FileMode.TYPE_FILE:
				case FileMode.TYPE_SYMLINK:
					ret.add(objectId);
					continue;
				default:

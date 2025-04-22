		if (!walksIgnoredDirectories() && isEntryIgnored()) {
			DirCacheIterator iterator = getDirCacheIterator();
			if (iterator == null) {
				return new EmptyTreeIterator(this);
			}
		}
		return enterSubtree();
	}


	protected AbstractTreeIterator enterSubtree() {
		return new FileTreeIterator(this
				fileModeStrategy);

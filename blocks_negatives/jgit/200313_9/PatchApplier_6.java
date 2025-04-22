			throws PatchApplyException {
		try {
			if (inCore()) {
				return TreeWalk.forPath(repo, path, beforeTree);
			}
			TreeWalk walk = new TreeWalk(repo);

			int cacheTreeIdx = walk.addTree(new DirCacheIterator(cache));
			FileTreeIterator files = new FileTreeIterator(repo);
			if (FILE_TREE_INDEX != walk.addTree(files))
				throw new IllegalStateException();

			walk.setFilter(AndTreeFilter.create(
					PathFilterGroup.createFromStrings(path),
					new NotIgnoredFilter(FILE_TREE_INDEX)));
			walk.setOperationType(OperationType.CHECKIN_OP);
			walk.setRecursive(true);
			files.setDirCacheIterator(walk, cacheTreeIdx);
			return walk;
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(
					JGitText.get().patchApplyException, e.getMessage()), e);

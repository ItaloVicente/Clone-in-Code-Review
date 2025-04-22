	private static void cleanupUntracked(Repository db
			throws NoWorkTreeException
		FileTreeIterator workingTreeIt = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.setFilter(
				PathFilterGroup.createFromStrings(entry.getPathString()));
		diff.diff();
		Set<String> paths = diff.getUntracked();
		for (String path : paths) {
			FileUtils.delete(new File(db.getWorkTree()
		}
		if (!paths.isEmpty()) {
			FileUtils.delete(new File(db.getWorkTree()
					FileUtils.EMPTY_DIRECTORIES_ONLY | FileUtils.RECURSIVE);
		}
	}


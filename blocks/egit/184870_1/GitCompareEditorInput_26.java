		DiffContainer result = new DiffNode(Differencer.CONFLICTING);

		Repository repo = getRepository();
		try (TreeWalk tw = new TreeWalk(repo)) {
			int leftIndex;
			int dirCacheIndex = -1;

			if (leftCommit == null) {
				FileTreeIterator fit = new FileTreeIterator(repo);
				leftIndex = tw.addTree(fit);
				dirCacheIndex = tw
						.addTree(new DirCacheIterator(repo.readDirCache()));
				fit.setDirCacheIterator(tw, dirCacheIndex);
			} else {
				leftIndex = tw.addTree(new CanonicalTreeParser(null,
						repo.newObjectReader(), leftCommit.getTree()));
			}
			int rightIndex = tw.addTree(new CanonicalTreeParser(null,
					repo.newObjectReader(), rightCommit.getTree()));

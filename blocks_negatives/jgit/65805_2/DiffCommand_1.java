		try {
			if (cached) {
				if (oldTree == null) {
					if (head == null)
						throw new NoHeadException(JGitText.get().cannotReadTree);
					CanonicalTreeParser p = new CanonicalTreeParser();
					try (ObjectReader reader = repo.newObjectReader()) {
						p.reset(reader, head);
					}
					oldTree = p;
				}
				newTree = new DirCacheIterator(repo.readDirCache());
			} else {
				if (oldTree == null)
					oldTree = new DirCacheIterator(repo.readDirCache());
				if (newTree == null)
					newTree = new FileTreeIterator(repo);
			}

			diffFmt.setPathFilter(pathFilter);

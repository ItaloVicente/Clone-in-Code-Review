			else
				startWalk.addTree(new DirCacheIterator(dc));

			final File workTree = repo.getWorkTree();
			final ObjectReader r = repo.getObjectDatabase().newReader();
			try {

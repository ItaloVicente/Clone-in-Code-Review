			DirCacheIterator dci = null;
			if (fti != null) {
				dci = new DirCacheIterator(DirCache.read(repo));
				tw.addTree(dci);
				fti.setDirCacheIterator(tw, 3);
			}

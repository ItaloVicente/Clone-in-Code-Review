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

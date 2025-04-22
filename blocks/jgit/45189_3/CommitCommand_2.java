		try (TreeWalk treeWalk = new TreeWalk(repo)) {
			int dcIdx = treeWalk
					.addTree(new DirCacheBuildIterator(existingBuilder));
			int fIdx = treeWalk.addTree(new FileTreeIterator(repo));
			int hIdx = -1;
			if (headId != null)
				hIdx = treeWalk.addTree(rw.parseTree(headId));
			treeWalk.setRecursive(true);

			String lastAddedFile = null;
			while (treeWalk.next()) {
				String path = treeWalk.getPathString();
				int pos = lookupOnly(path);

				CanonicalTreeParser hTree = null;
				if (hIdx != -1)
					hTree = treeWalk.getTree(hIdx

				DirCacheIterator dcTree = treeWalk.getTree(dcIdx
						DirCacheIterator.class);

				if (pos >= 0) {

					FileTreeIterator fTree = treeWalk.getTree(fIdx
							FileTreeIterator.class);

					boolean tracked = dcTree != null || hTree != null;
					if (!tracked)
						break;

					if (path.equals(lastAddedFile))
						continue;

					lastAddedFile = path;

					if (fTree != null) {
						final DirCacheEntry dcEntry = new DirCacheEntry(path);
						long entryLength = fTree.getEntryLength();
						dcEntry.setLength(entryLength);
						dcEntry.setLastModified(fTree.getEntryLastModified());
						dcEntry.setFileMode(fTree.getIndexFileMode(dcTree));

						boolean objectExists = (dcTree != null
								&& fTree.idEqual(dcTree))
								|| (hTree != null && fTree.idEqual(hTree));
						if (objectExists) {

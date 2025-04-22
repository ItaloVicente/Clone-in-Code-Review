	private DirCache createTemporaryIndex(ObjectId headId
			throws IOException {
		ObjectInserter inserter = null;

		DirCacheEditor dcEditor = index.editor();

		DirCache inCoreIndex = DirCache.newInCore();
		DirCacheBuilder dcBuilder = inCoreIndex.builder();

		onlyProcessed = new boolean[only.size()];
		boolean emptyCommit = true;

		TreeWalk treeWalk = new TreeWalk(repo);
		int dcIdx = treeWalk.addTree(new DirCacheIterator(index));
		int fIdx = treeWalk.addTree(new FileTreeIterator(repo));
		int hIdx = -1;
		if (headId != null)
			hIdx = treeWalk.addTree(new RevWalk(repo).parseTree(headId));
		treeWalk.setRecursive(true);

		while (treeWalk.next()) {
			String path = treeWalk.getPathString();
			int pos = lookupOnly(path);

			CanonicalTreeParser hTree = null;
			if (hIdx != -1)
				hTree = treeWalk.getTree(hIdx

			if (pos >= 0) {

				DirCacheIterator dcTree = treeWalk.getTree(dcIdx
						DirCacheIterator.class);
				FileTreeIterator fTree = treeWalk.getTree(fIdx
						FileTreeIterator.class);

				boolean tracked = dcTree != null || hTree != null;
				if (!tracked)
					break;

				if (fTree != null) {
					final DirCacheEntry dcEntry = new DirCacheEntry(path);
					long entryLength = fTree.getEntryLength();
					dcEntry.setLength(entryLength);
					dcEntry.setLastModified(fTree.getEntryLastModified());
					dcEntry.setFileMode(fTree.getEntryFileMode());

					boolean objectExists = (dcTree != null && fTree
							.idEqual(dcTree))
							|| (hTree != null && fTree.idEqual(hTree));
					if (objectExists) {
						dcEntry.setObjectId(fTree.getEntryObjectId());
					} else {
						if (inserter == null)
							inserter = repo.newObjectInserter();

						InputStream inputStream = fTree.openEntryStream();
						try {
							dcEntry.setObjectId(inserter.insert(
									Constants.OBJ_BLOB
									inputStream));
						} finally {
							inputStream.close();
						}
					}

					dcEditor.add(new PathEdit(path) {
						@Override
						public void apply(DirCacheEntry ent) {
							ent.copyMetaData(dcEntry);
						}
					});
					dcBuilder.add(dcEntry);

					if (emptyCommit && (hTree == null || !hTree.idEqual(fTree)))
						emptyCommit = false;
				} else {
					dcEditor.add(new DeletePath(path));

					if (emptyCommit && hTree != null)
						emptyCommit = false;
				}

				onlyProcessed[pos] = true;
			} else {
				if (hTree != null) {
					final DirCacheEntry dcEntry = new DirCacheEntry(path);
					dcEntry.setObjectId(hTree.getEntryObjectId());
					dcEntry.setFileMode(hTree.getEntryFileMode());

					dcBuilder.add(dcEntry);
				}
			}
		}

		for (int i = 0; i < onlyProcessed.length; i++)
			if (!onlyProcessed[i])
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().entryNotFoundByPath

		if (emptyCommit)
			throw new JGitInternalException(JGitText.get().emptyCommit);

		dcEditor.commit();
		dcBuilder.finish();
		return inCoreIndex;
	}

	private int lookupOnly(String pathString) {
		int i = 0;
		for (String o : only) {
			String p = pathString;
			while (true) {
				if (p.equals(o))
					return i;
				int l = p.lastIndexOf("/");
				if (l < 1)
					break;
				p = p.substring(0
			}
			i++;
		}
		return -1;
	}


	private void scanForConflicts(TreeWalk treeWalk) throws IOException {
		File workingTree = repo.getWorkTree();
		while (treeWalk.next()) {
			AbstractTreeIterator stashIndexIter = treeWalk.getTree(1,
					AbstractTreeIterator.class);
			AbstractTreeIterator stashWorkingIter = treeWalk.getTree(2,
					AbstractTreeIterator.class);

			AbstractTreeIterator headIter = treeWalk.getTree(3,
					AbstractTreeIterator.class);
			AbstractTreeIterator indexIter = treeWalk.getTree(4,
					AbstractTreeIterator.class);
			AbstractTreeIterator workingIter = treeWalk.getTree(5,
					AbstractTreeIterator.class);

			if (isConflict(stashIndexIter, stashWorkingIter, headIter,
					indexIter, workingIter)) {
				String path = treeWalk.getPathString();
				File file = new File(workingTree, path);
				throw new CheckoutConflictException(file.getAbsolutePath());
			}
		}
	}

	private void applyChanges(TreeWalk treeWalk, DirCache cache,
			DirCacheEditor editor) throws IOException {
		File workingTree = repo.getWorkTree();
		while (treeWalk.next()) {
			String path = treeWalk.getPathString();
			File file = new File(workingTree, path);

			AbstractTreeIterator stashHeadIter = treeWalk.getTree(0,
					AbstractTreeIterator.class);
			AbstractTreeIterator stashIndexIter = treeWalk.getTree(1,
					AbstractTreeIterator.class);
			AbstractTreeIterator stashWorkingIter = treeWalk.getTree(2,
					AbstractTreeIterator.class);

			if (stashWorkingIter != null && stashIndexIter != null) {
				DirCacheEntry entry = cache.getEntry(path);
				if (entry == null)
					entry = new DirCacheEntry(treeWalk.getRawPath());
				entry.setFileMode(stashIndexIter.getEntryFileMode());
				entry.setObjectId(stashIndexIter.getEntryObjectId());
				DirCacheCheckout.checkoutEntry(repo, file, entry,
						treeWalk.getObjectReader());
				final DirCacheEntry updatedEntry = entry;
				editor.add(new PathEdit(path) {

					public void apply(DirCacheEntry ent) {
						ent.copyMetaData(updatedEntry);
					}
				});

				if (!stashWorkingIter.idEqual(stashIndexIter)) {
					entry = new DirCacheEntry(treeWalk.getRawPath());
					entry.setObjectId(stashWorkingIter.getEntryObjectId());
					DirCacheCheckout.checkoutEntry(repo, file, entry,
							treeWalk.getObjectReader());
				}
			} else {
				if (stashIndexIter == null
						|| (stashHeadIter != null && !stashIndexIter
								.idEqual(stashHeadIter)))
					editor.add(new DeletePath(path));
				FileUtils
						.delete(file, FileUtils.RETRY | FileUtils.SKIP_MISSING);
			}
		}
	}


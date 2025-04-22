	private void checkoutPathsFromIndex(TreeWalk treeWalk
			throws IOException {
		DirCacheIterator dci = new DirCacheIterator(dc);
		treeWalk.addTree(dci);

		final ObjectReader r = treeWalk.getObjectReader();
		DirCacheEditor editor = dc.editor();
		while (treeWalk.next()) {
			DirCacheEntry entry = dci.getDirCacheEntry();
			if (entry != null && entry.getStage() > DirCacheEntry.STAGE_1)
				continue;
			editor.add(new PathEdit(treeWalk.getPathString()) {
				public void apply(DirCacheEntry ent) {
					int stage = ent.getStage();
					if (stage > DirCacheEntry.STAGE_0) {
						if (checkoutStage != null) {
							if (stage == checkoutStage.number)
								checkoutPath(ent
						} else {
							UnmergedPathException e = new UnmergedPathException(
									ent);
							throw new JGitInternalException(e.getMessage()
						}
					} else {
						checkoutPath(ent
					}
				}
			});
		}
		editor.commit();
	}

	private void checkoutPathsFromCommit(TreeWalk treeWalk
			RevCommit commit) throws IOException {
		treeWalk.addTree(commit.getTree());
		final ObjectReader r = treeWalk.getObjectReader();
		DirCacheEditor editor = dc.editor();
		while (treeWalk.next()) {
			final ObjectId blobId = treeWalk.getObjectId(0);
			final FileMode mode = treeWalk.getFileMode(0);
			editor.add(new PathEdit(treeWalk.getPathString()) {
				public void apply(DirCacheEntry ent) {
					ent.setObjectId(blobId);
					ent.setFileMode(mode);
					checkoutPath(ent
				}
			});
		}
		editor.commit();
	}

	private void checkoutPath(DirCacheEntry entry
		File file = new File(repo.getWorkTree()
		File parentDir = file.getParentFile();
		try {
			FileUtils.mkdirs(parentDir
			DirCacheCheckout.checkoutEntry(repo
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().checkoutConflictWithFile
					entry.getPathString())
		}
	}

	private boolean isCheckoutIndex() {
		return startCommit == null && startPoint == null;
	}


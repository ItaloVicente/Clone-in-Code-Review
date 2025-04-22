
	private void resetUntracked(RevTree tree) throws CheckoutConflictException
			IOException {
		TreeWalk walk = null;
		try {
			walk.addTree(tree);
			walk.addTree(new FileTreeIterator(repo));
			walk.setRecursive(true);

			final ObjectReader reader = walk.getObjectReader();

			while (walk.next()) {
				final AbstractTreeIterator cIter = walk.getTree(0
						AbstractTreeIterator.class);
				if (cIter == null)
					continue;

				final DirCacheEntry entry = new DirCacheEntry(walk.getRawPath());
				entry.setFileMode(cIter.getEntryFileMode());
				entry.setObjectIdFromRaw(cIter.idBuffer()

				FileTreeIterator fIter = walk
						.getTree(1
				if (fIter != null) {
					if (fIter.isModified(entry
						throw new CheckoutConflictException(
								entry.getPathString());
					}
				}

				checkoutPath(entry
			}
		} finally {
			if (walk != null)
				walk.release();
		}
	}

	private void checkoutPath(DirCacheEntry entry
		try {
			File file = new File(repo.getWorkTree()
			DirCacheCheckout.checkoutEntry(repo
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().checkoutConflictWithFile
					entry.getPathString())
		}
	}

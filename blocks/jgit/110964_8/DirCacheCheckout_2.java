	private void keep(DirCacheEntry entry) throws IOException {
		if (entry != null && !FileMode.TREE.equals(entry.getFileMode())) {
			final File workingTreeFile = new File(repo.getWorkTree()
					entry.getPathString());
			final boolean skipSparse = skipSparse(entry);
			final boolean workingTreeFileExists = repo.getFS()
					.exists(workingTreeFile);

			if (!skipSparse && !workingTreeFileExists) {
				DirCacheEntry entryToRestore = new DirCacheEntry(
						entry.getPathString()
				entryToRestore.setFileMode(entry.getFileMode());
				entryToRestore.setObjectId(entry.getObjectId());
				updated.put(entryToRestore.getPathString()
						new CheckoutMetadata(walk.getEolStreamType(CHECKOUT_OP)
								walk.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_SMUDGE)));
				builder.add(entryToRestore);
			} else if (skipSparse && !entry.isSkipWorkTree()) {
				entry.setSkipWorkTree(true);
				removed.add(entry.getPathString());
			} else {
				builder.add(entry);
			}
		}

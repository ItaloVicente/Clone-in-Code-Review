			try (RevWalk revWalk = new RevWalk(db);
					TreeWalk treeWalk = new TreeWalk(db
							revWalk.getObjectReader())) {
				treeWalk.setFilter(
						PathFilterGroup.createFromStrings(mergedFilePath));
				DirCacheIterator cacheIter = new DirCacheIterator(cache);
				treeWalk.addTree(cacheIter);
				while (treeWalk.next()) {
					if (treeWalk.isSubtree()) {
						treeWalk.enterSubtree();
						continue;
					}
					final EolStreamType eolStreamType = treeWalk
							.getEolStreamType(CHECKOUT_OP);
					final String filterCommand = treeWalk.getFilterCommand(
							Constants.ATTR_FILTER_TYPE_SMUDGE);
					WorkingTreeOptions opt = db.getConfig()
							.get(WorkingTreeOptions.KEY);
					CheckoutMetadata checkoutMetadata = new CheckoutMetadata(
							eolStreamType
					DirCacheEntry entry = treeWalk.getTree(DirCacheIterator.class).getDirCacheEntry();
					if (entry == null) {
						continue;
					}

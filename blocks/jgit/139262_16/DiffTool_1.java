	private FileElement createFileElement(FileElement.Type elementType
			Pair pair
			throws NoWorkTreeException
			ToolException {
		String entryPath = side == Side.NEW ? entry.getNewPath()
				: entry.getOldPath();
		FileElement fileElement = new FileElement(entryPath
		if (!pair.isWorkingTreeSource(side) && !fileElement.isNullPath()) {
			try (RevWalk revWalk = new RevWalk(db);
					TreeWalk treeWalk = new TreeWalk(db
							revWalk.getObjectReader())) {
				treeWalk.setFilter(
						PathFilterGroup.createFromStrings(entryPath));
				if (side == Side.NEW) {
					newTree.reset();
					treeWalk.addTree(newTree);
				} else {
					oldTree.reset();
					treeWalk.addTree(oldTree);
				}
				if (treeWalk.next()) {
					final EolStreamType eolStreamType = treeWalk
							.getEolStreamType(CHECKOUT_OP);
					final String filterCommand = treeWalk.getFilterCommand(
							Constants.ATTR_FILTER_TYPE_SMUDGE);
					WorkingTreeOptions opt = db.getConfig()
							.get(WorkingTreeOptions.KEY);
					CheckoutMetadata checkoutMetadata = new CheckoutMetadata(
							eolStreamType
					DirCacheCheckout.checkoutToFile(db
							checkoutMetadata
							db.getFS()
				} else {
							+ "' in staging area!"
				}

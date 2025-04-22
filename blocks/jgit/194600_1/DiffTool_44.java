	private FileElement createFileElement(FileElement.Type elementType
			Pair pair
			CorruptObjectException
		String entryPath = side == Side.NEW ? entry.getNewPath()
				: entry.getOldPath();
		FileElement fileElement = new FileElement(entryPath
				db.getWorkTree());
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
					DirCacheCheckout.getContent(db
							checkoutMetadata
							new FileOutputStream(
									fileElement.createTempFile(null)));
				} else {
							+ "' in staging area!"
							null);
				}
			}
		}
		return fileElement;
	}

	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator) {
			return ContentSource.create((WorkingTreeIterator) iterator);
		}
		return ContentSource.create(db.newObjectReader());
	}


		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid, but not currently available:"); //$NON-NLS-1$
		for (String name : predefTools.keySet()) {
			if (!predefTools.get(name).isAvailable()) {
			}
		}
		outw.println(
				"environment. If run in a terminal-only session, they will fail."); //$NON-NLS-1$
		outw.flush();
		return;
	}

	private List<DiffEntry> getFiles()
			throws RevisionSyntaxException, AmbiguousObjectException,
			IncorrectObjectTypeException, IOException {
		diffFmt.setRepository(db);
		if (cached) {
			if (oldTree == null) {
				if (head == null) {
					die(MessageFormat.format(CLIText.get().notATree, HEAD));
				}
				CanonicalTreeParser p = new CanonicalTreeParser();
				try (ObjectReader reader = db.newObjectReader()) {
					p.reset(reader, head);
				}
				oldTree = p;
			}
			newTree = new DirCacheIterator(db.readDirCache());
		} else if (oldTree == null) {
			oldTree = new DirCacheIterator(db.readDirCache());
			newTree = new FileTreeIterator(db);
		} else if (newTree == null) {
			newTree = new FileTreeIterator(db);
		}

		TextProgressMonitor pm = new TextProgressMonitor(errw);
		pm.setDelayStart(2, TimeUnit.SECONDS);
		diffFmt.setProgressMonitor(pm);
		diffFmt.setPathFilter(pathFilter);

		List<DiffEntry> files = diffFmt.scan(oldTree, newTree);
		return files;
	}

	private FileElement createFileElement(FileElement.Type elementType,
			Pair pair, Side side, DiffEntry entry)
			throws NoWorkTreeException, CorruptObjectException, IOException,
			ToolException {
		String entryPath = side == Side.NEW ? entry.getNewPath()
				: entry.getOldPath();
		FileElement fileElement = new FileElement(entryPath, elementType);
		if (!pair.isWorkingTreeSource(side) && !fileElement.isNullPath()) {
			try (RevWalk revWalk = new RevWalk(db);
					TreeWalk treeWalk = new TreeWalk(db,
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
							eolStreamType, filterCommand);
					DirCacheCheckout.checkoutToFile(db, entryPath,
							checkoutMetadata, pair.open(side, entry),
							db.getFS(), opt, fileElement.createTempFile(null));
				} else {
							+ "' in staging area!", null); //$NON-NLS-1$
				}
			}
		}
		return fileElement;
	}

	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator)
			return ContentSource.create((WorkingTreeIterator) iterator);
		return ContentSource.create(db.newObjectReader());
	}

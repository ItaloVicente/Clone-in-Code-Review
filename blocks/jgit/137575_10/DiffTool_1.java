	private void showToolHelp() throws IOException {
		outw.println(
		for (String name : diffToolMgr.getAvailableTools().keySet()) {
		}
		Map<String
		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid
		for (String name : diffToolMgr.getNotAvailableTools().keySet()) {
		}
		outw.println(
				"environment. If run in a terminal-only session
		return;
	}

	private List<DiffEntry> getFiles()
			throws RevisionSyntaxException
			IncorrectObjectTypeException
		diffFmt.setRepository(db);
		if (cached) {
			if (oldTree == null) {
				if (head == null) {
					die(MessageFormat.format(CLIText.get().notATree
				}
				CanonicalTreeParser p = new CanonicalTreeParser();
				try (ObjectReader reader = db.newObjectReader()) {
					p.reset(reader
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
		pm.setDelayStart(2
		diffFmt.setProgressMonitor(pm);
		diffFmt.setPathFilter(pathFilter);

		List<DiffEntry> files = diffFmt.scan(oldTree
		return files;
	}

	private ObjectStream getObjectStream(Pair pair
		ObjectStream stream = null;
		if (!pair.isWorkingTreeSource(side)) {
			try {
				stream = pair.open(side
			} catch (Exception e) {
				stream = null;
			}
		}
		return stream;

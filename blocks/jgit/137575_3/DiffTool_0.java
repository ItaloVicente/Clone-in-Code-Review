	private void compare(List<DiffEntry> files
			String toolNamePrompt) throws IOException {
		ContentSource.Pair sourcePair = new ContentSource.Pair(
				source(oldTree)
		for (int fileIndex = 0; fileIndex < files.size(); fileIndex++) {
			DiffEntry ent = files.get(fileIndex);
			String mergedFilePath = ent.getNewPath();
			if (mergedFilePath.equals(DiffEntry.DEV_NULL)) {
				mergedFilePath = ent.getOldPath();
			}
			FileElement local = new FileElement(ent.getOldPath()
					ent.getOldId().name()
					getObjectStream(sourcePair
			FileElement remote = new FileElement(ent.getNewPath()
					ent.getNewId().name()
							? getObjectStream(sourcePair
									ent)
							: null);
			boolean launchCompare = true;
			if (showPrompt) {
				launchCompare = isLaunchCompare(fileIndex + 1
						files.size()
						toolNamePrompt);
			}
			if (launchCompare) {
				try {
					diffToolMgr.compare(db
							mergedFilePath
							trustExitCode);
				} catch (DiffToolException e) {
					outw.println(e.getMessage());
					throw die("external diff died
							+ mergedFilePath
				}
			} else {
				break;
			}
		}
	}

	private boolean isLaunchCompare(int fileIndex
			int fileCount
			throws IOException {
		boolean launchCompare = true;
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		if ((line = br.readLine()) != null) {
				launchCompare = false;
			}
		}
		return launchCompare;
	}
	private void showToolHelp() throws IOException {
		outw.println(
		for (String name : diffToolMgr.getAvailableTools().keySet()) {
		}
		Map<String
				.getUserDefinedTools();
		for (String name : userTools.keySet()) {
					+ userTools.get(name).getCommand());
		}
		outw.println(
				"The following tools are valid
		for (String name : diffToolMgr.getNotAvailableTools()
				.keySet()) {
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
		try {
			stream = pair.open(side
		} catch (Exception e) {
			stream = null;
		}
		return stream;
	}


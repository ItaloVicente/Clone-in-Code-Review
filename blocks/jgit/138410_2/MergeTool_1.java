				mergeResult = MergeResult.ABORT;
			}
		}
	}

	private MergeResult mergeModified(final String mergedFilePath
			final boolean showPrompt
			throws Exception {
		boolean launch = true;
		if (showPrompt) {
			launch = isLaunch(toolNamePrompt);
		}
		if (!launch) {
		}
		boolean isMergeSuccessful = true;
		ContentSource baseSource = ContentSource.create(db.newObjectReader());
		ContentSource localSource = ContentSource.create(db.newObjectReader());
		ContentSource remoteSource = ContentSource.create(db.newObjectReader());
		try {
			FileElement base = null;
			FileElement local = null;
			FileElement remote = null;
			DirCache cache = db.readDirCache();
			int firstIndex = cache.findEntry(mergedFilePath);
			if (firstIndex >= 0) {
				int nextIndex = cache.nextEntry(firstIndex);
				for (; firstIndex < nextIndex; firstIndex++) {
					DirCacheEntry entry = cache.getEntry(firstIndex);
					ObjectId id = entry.getObjectId();
					switch (entry.getStage()) {
					case DirCacheEntry.STAGE_1:
						base = new FileElement(mergedFilePath
								baseSource.open(mergedFilePath
										.openStream());
						break;
					case DirCacheEntry.STAGE_2:
						local = new FileElement(mergedFilePath
								localSource.open(mergedFilePath
										.openStream());
						break;
					case DirCacheEntry.STAGE_3:
						remote = new FileElement(mergedFilePath
								remoteSource.open(mergedFilePath
										.openStream());
						break;
					}
				}
			}
			if ((local == null) || (remote == null)) {
				throw die(
						"local or remote cannot be found in cache
								+ mergedFilePath);
			}
			File merged = new File(mergedFilePath);
			long modifiedBefore = merged.lastModified();
			try {
				ExecutionResult executionResult = mergeToolMgr.merge(db
						remote
				outw.println(
						new String(executionResult.getStdout().toByteArray()));
				outw.flush();
			} catch (ToolException e) {
				isMergeSuccessful = false;
				outw.println(e.getResultStdout());
				outw.flush();
				errw.flush();
				if (e.isCommandExecutionError()) {
					errw.println(e.getMessage());
					throw die("excution error"
							e);
				}
			}
			if (isMergeSuccessful) {
				long modifiedAfter = merged.lastModified();
				if (modifiedBefore == modifiedAfter) {
					isMergeSuccessful = isMergeSuccessful();
				}
			}
			if (isMergeSuccessful) {
				addFile(mergedFilePath);
			}
		} finally {
			baseSource.close();
			localSource.close();
			remoteSource.close();
		}
		return isMergeSuccessful ? MergeResult.SUCCESSFUL : MergeResult.FAILED;
	}

	private MergeResult mergeDeleted(final String mergedFilePath
			final boolean deletedByUs) throws Exception {
		if (deletedByUs) {
		} else {
		}
		int mergeDecision = getDeletedMergeDecision();
		if (mergeDecision == 1) {
			addFile(mergedFilePath);
		} else if (mergeDecision == -1) {
			rmFile(mergedFilePath);
		} else {
			return MergeResult.ABORT;
		}
		return MergeResult.SUCCESSFUL;
	}

	private void addFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.add().addFilepattern(fileName).call();
		}
	}

	private void rmFile(String fileName) throws Exception {
		try (Git git = new Git(db)) {
			git.rm().addFilepattern(fileName).call();
		}
	}

	private boolean hasUserAccepted(final String message)
			throws IOException {
		boolean yes = true;
		outw.print(message);
		outw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String line = null;
		while ((line = br.readLine()) != null) {
				yes = true;
				break;
				yes = false;

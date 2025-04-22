				outw.println(MessageFormat.format(
						CLIText.get().mergeToolUnknownConflict
						mergedFilePath));
				mergeResult = MergeResult.ABORTED;
			}
		}
	}

	private MergeResult mergeModified(String mergedFilePath
			String toolNamePrompt) throws Exception {
		outw.println(MessageFormat.format(CLIText.get().mergeToolNormalConflict
				mergedFilePath));
		outw.flush();
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
				throw die(MessageFormat.format(CLIText.get().mergeToolDied
						mergedFilePath));
			}
			File merged = new File(mergedFilePath);
			long modifiedBefore = merged.lastModified();
			try {
				ExecutionResult executionResult = mergeTools.merge(db
						remote

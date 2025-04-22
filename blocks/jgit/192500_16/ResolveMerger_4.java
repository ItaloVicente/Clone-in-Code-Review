	private void cleanUpWorkingTree(CanonicalTreeParser ours
			throws IOException {
		if(ours != null  && !ours.getEntryPathString().equals(treeWalk.getPathString()) && nonTree(ours.getEntryRawMode()) && (treeWalk.getTreeCount() > T_FILE && treeWalk.getRawMode(T_FILE) != 0)){
			addDeletion(ours.getEntryPathString()
		}
	}

	private CanonicalTreeParser parserFor(AnyObjectId id)
			throws IncorrectObjectTypeException
		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(reader
		return p;
	}

	private void reportRenameConflict(String originalPath
			throws IOException {
		if(!this.renameResolver.baseRenamePaths.containsKey(originalPath)){
			return;
		}
		Map<Integer
		switch (renameType) {
			case RENAME_BOTH_SIDES_CONFLICT: {
				reportRenameRenameConflict(originalPath
				break;
			}
			case RENAME_DELETE_CONFLICT: {
				int renameSide = renamePaths.containsKey(T_OURS) ? T_OURS : T_THEIRS;
				String targetPath = renamePaths.get(renameSide);
				reportRenameDeleteConflict(renameSide
						renameSide == T_OURS ? ours : null
				break;
			}
		}
	}

	private void reportRenameRenameConflict( String originalPath
			throws IOException {
		MergeResult conflictResult = new MergeResult<>(Collections.emptyList());
		conflictResult.setContainsConflicts(true);
		TreeWalk baseWalk = getRenameWalk(base
		TreeWalk oursWalk = getRenameWalk(ours
		TreeWalk theirsWalk = getRenameWalk(theirs
		add(baseWalk.getRawPath()
				DirCacheEntry.STAGE_1
		add(oursWalk.getRawPath()
				DirCacheEntry.STAGE_2
		DirCacheEntry theirsEntry = add(theirsWalk.getRawPath()
				DirCacheEntry.STAGE_3
		if (theirsEntry != null && nonTree(theirsEntry.getRawMode())) {
			Attributes[] attributes = {NO_ATTRIBUTES
			attributes[T_THEIRS] =
					theirsWalk.hasAttributeNodeProvider() ? theirsWalk.getAttributes() : NO_ATTRIBUTES;
			addToCheckout(theirsEntry.getPathString()
		}
		unmergedPaths.add(originalPath);
		unmergedPaths.add(renamePaths.get(T_OURS));
		unmergedPaths.add(renamePaths.get(T_THEIRS));
		mergeResults.put(originalPath
		mergeResults.put(renamePaths.get(T_OURS)
		mergeResults.put(renamePaths.get(T_THEIRS)

	}

	private void reportRenameDeleteConflict(int renameSide
			throws IOException {
		MergeResult conflictResult = new MergeResult<>(Collections.emptyList());
		conflictResult.setContainsConflicts(true);
		TreeWalk baseWalk = getRenameWalk(base
		TreeWalk oursWalk = ours != null ? getRenameWalk(ours
		TreeWalk theirsWalk = theirs != null ? getRenameWalk(theirs
		Attributes[] attributes = {NO_ATTRIBUTES
		if(tw.hasAttributeNodeProvider()) {
			attributes[T_BASE] = baseWalk.getAttributes();
			attributes[T_OURS] = oursWalk != null ? oursWalk.getAttributes() : NO_ATTRIBUTES;
			attributes[T_THEIRS] =
			attributes[T_THEIRS] =
					theirsWalk != null ? theirsWalk.getAttributes() : NO_ATTRIBUTES;
		}
		byte[] rawTargetPath =
				renameSide == T_OURS ? oursWalk.getRawPath() : theirsWalk.getRawPath();
		add(rawTargetPath
				DirCacheEntry.STAGE_1
		if(oursWalk != null) {
			add(rawTargetPath
					DirCacheEntry.STAGE_2
		}
		if(theirsWalk != null) {
			DirCacheEntry theirsEntry = add(rawTargetPath
					DirCacheEntry.STAGE_3
			if (theirsEntry != null && nonTree(theirsEntry.getRawMode())) {
				addToCheckout(targetPath
			}
		}
		unmergedPaths.add(targetPath);
		mergeResults.put(targetPath
	}


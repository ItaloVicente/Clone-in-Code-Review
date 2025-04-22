
	private void updateWorkingTree(CanonicalTreeParser ours
			throws IOException {
		boolean hasWorkingTreeEntry =
				tw.getTreeCount() > T_FILE && treeWalk.getRawMode(T_FILE) != 0;
		if (!hasWorkingTreeEntry || ours == null) {
			return;
		}
		String oldPath = ours.getEntryPathString();
		if (!oldPath.equals(treeWalk.getPathString()) && !renameResolver.isTargetRename(oldPath)
				&& nonTree(
				ours.getEntryRawMode())) {
			addDeletion(ours.getEntryPathString()
		}
	}

	private void reportRenameConflict(RenameEntry renameEntry
			throws IOException {
		if(ignoreConflicts){
			return;
		}
		switch (renameEntry.renameConflict) {
			case RENAME_BOTH_SIDES_CONFLICT: {
				reportRenameRenameConflict(renameEntry
				break;
			}
			case RENAME_DELETE_CONFLICT: {
				reportRenameDeleteConflict(renameEntry
				break;
			}
		}
	}


	private boolean checkWorkTreeDirty(RevTree baseTree
			throws IOException {
		Set<String> renamePaths = new HashSet<>();
		for (Entry<String
			if(rename.getValue().getRenameType().equals(RENAME_IN_THEIRS)) {
				renamePaths.add(rename.getKey());
			}
			renamePaths.add(rename.getValue().getTargetPath());
		}
		for (Entry<String
			renamePaths.addAll(rename.getValue().getTargetPaths());
		}
		if(renamePaths.isEmpty()){
			return false;
		}
		TreeFilter renamePathsFilter = PathFilterGroup.createFromStrings(renamePaths);
		TreeWalk indexTw = new TreeWalk(db
		indexTw.addTree(baseTree == null ? new EmptyTreeIterator() : openTree(baseTree));
		indexTw.addTree(headTree);
		indexTw.addTree(mergeTree);
		indexTw.setFilter(renamePathsFilter);
		DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder
		int dciPos = indexTw.addTree(buildIt);
		if (workingTreeIterator != null) {
			workingTreeIterator.reset();
			indexTw.addTree(workingTreeIterator);
			workingTreeIterator.setDirCacheIterator(indexTw
		} else {
			indexTw.setFilter(TreeFilter.ANY_DIFF);
		}
		indexTw.setAttributesNodeProvider(tw.getAttributesNodeProvider());

		tw = indexTw;
		while (indexTw.next()){
			if(!checkIndexAndWorkTree(indexTw.getTree(T_INDEX
					workingTreeIterator != null ? indexTw.getTree(T_FILE
							WorkingTreeIterator.class) : null)){
				return true;
			}
		}
		return false;
	}

	private DirCacheEntry oursDce(DirCacheBuildIterator index){
		DirCacheEntry ourDce = null;
		if (index == null || index.getDirCacheEntry() == null) {
			if (nonTree(tw.getRawMode(T_OURS))) {
				ourDce = new DirCacheEntry(tw.getRawPath(T_OURS));
				ourDce.setObjectId(tw.getObjectId(T_OURS));
				ourDce.setFileMode(tw.getFileMode(T_OURS));
			}
		} else {
			ourDce = index.getDirCacheEntry();
		}
		return ourDce;
	}

	private boolean checkIndexAndWorkTree(DirCacheBuildIterator index
			throws IOException {
		DirCacheEntry ourDce = oursDce(index);
		return !isIndexDirty() && !isWorktreeDirty(work
	}

	private void reportRenameRenameConflict(RenameEntry renameEntry
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
		unmergedPaths.add(renameEntry.getSourcePath());
		unmergedPaths.add(renameEntry.getTargetPath(T_OURS));
		unmergedPaths.add(renameEntry.getTargetPath(T_THEIRS));
		mergeResults.put(renameEntry.getSourcePath()
		mergeResults.put(renameEntry.getTargetPath(T_OURS)
		mergeResults.put(renameEntry.getTargetPath(T_THEIRS)

	}

	private void reportRenameDeleteConflict(RenameEntry renameEntry
			throws IOException {
		int renameSide = renameEntry.getRenameType().equals(RENAME_IN_OURS)? T_OURS: T_THEIRS;
		MergeResult conflictResult = new MergeResult<>(Collections.emptyList());
		conflictResult.setContainsConflicts(true);
		TreeWalk baseWalk = getRenameWalk(base
		TreeWalk oursWalk = renameSide == T_OURS ? getRenameWalk(ours
		TreeWalk theirsWalk = renameSide == T_THEIRS ? getRenameWalk(theirs
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
				addToCheckout(renameEntry.getTargetPath()
			}
		}
		unmergedPaths.add(renameEntry.getTargetPath());
		mergeResults.put(renameEntry.getTargetPath()
	}


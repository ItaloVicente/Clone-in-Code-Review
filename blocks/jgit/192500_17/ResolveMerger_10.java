
	private DirCacheEntry addToIndexAndCheckout(CanonicalTreeParser p
		DirCacheEntry e = add(tw.getRawPath()
				DirCacheEntry.STAGE_0
		if (e != null) {
			addToCheckout(tw.getPathString()
		}
		return e;
	}

	private DirCacheEntry addOursToIndex(DirCacheEntry oursEntry
			throws IOException {

		if(!isRenameProcessing){
			return keep(oursEntry);
		}

		return addToIndexAndCheckout(ours
	}

	private TreeWalk getRenameWalk(Collection<RevTree> renameTrees
		TreeWalk renameWalk = new TreeWalk(db
		renameWalk.setAttributesNodeProvider(tw.getAttributesNodeProvider());
		for(RevTree tree: renameTrees) {
			renameWalk.addTree(tree);
		}
		renameWalk.addTree(new DirCacheBuildIterator(builder
		TreeWalk.walkToPath(renameWalk
		return renameWalk;
	}

	private TreeWalk getRenameWalk(RevTree renameTree
		return  getRenameWalk(List.of(renameTree)
	}

	private void setUpRenameWalk(RenameProcessingTreeWalk walk
			throws IOException {

		TreeWalk.walkToPath(walk

		boolean hasAttributeNodeProvider = walk.hasAttributeNodeProvider();
		if (hasAttributeNodeProvider) {
			attributes[T_BASE] = walk.getAttributes(T_BASE);
			attributes[T_OURS] = walk.getAttributes(T_OURS);
			attributes[T_THEIRS] = walk.getAttributes(T_THEIRS);
		}
		TreeWalk swapWalk = getRenameWalk(swapTreeNth.values()
		int i = 0;
		for (int swapNth : swapTreeNth.keySet()) {
			walk.swapRenameTree(swapNth
			if(hasAttributeNodeProvider) {
				attributes[swapNth] = swapWalk.getAttributes(i);
			}
			i++;
		}
	}

	private boolean processRenames(RevTree baseTree
			RevTree headTree
		for(Entry<String
			reportRenameConflict(sourceRenameConflict.getValue()
		}

		for (Entry<String

			RenameType renameType = baseRename.getValue().renameType;

			RenameProcessingTreeWalk indexTw = new RenameProcessingTreeWalk(db
			indexTw.addTree(baseTree == null ? new EmptyTreeIterator() : openTree(baseTree));
			indexTw.addTree(headTree);
			indexTw.addTree(mergeTree);
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

			Attributes[] attributes = {NO_ATTRIBUTES
					NO_ATTRIBUTES};;

			if (renameType.equals(RenameType.RENAME_BOTH_NO_CONFLICT)) {
				setUpRenameWalk(indexTw
			} else if (renameType.equals(RENAME_IN_OURS)) {
				setUpRenameWalk(indexTw
			} else if (renameType.equals(RENAME_IN_THEIRS)) {
				setUpRenameWalk(indexTw
			}

			indexTw.setPathName(baseRename.getValue().targetPath);
			tw = indexTw;
			boolean success = processEntry(indexTw.getTree(T_BASE
					indexTw.getTree(T_OURS
					indexTw.getTree(T_THEIRS
					indexTw.getTree(T_INDEX
					tw.getTreeCount() > T_FILE ? indexTw.getTree(T_FILE
							WorkingTreeIterator.class) : null
					attributes
			updateWorkingTree(indexTw.getTree(T_OURS
			if(!success){
				return false;
			}
		}
		return true;
	}

	private void updateWorkingTree(CanonicalTreeParser ours
			throws IOException {
		boolean hasWorkingTreeEntry= treeWalk.getTreeCount() > T_FILE && treeWalk.getRawMode(T_FILE) != 0 ;
		if(!hasWorkingTreeEntry || ours == null){
			return;
		}
		String oldPath = ours.getEntryPathString();
		if (!oldPath.equals(treeWalk.getPathString()) && !renameResolver.isTargetRename(oldPath) && nonTree(
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

	private void reportRenameRenameConflict( RenameEntry renameEntry
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


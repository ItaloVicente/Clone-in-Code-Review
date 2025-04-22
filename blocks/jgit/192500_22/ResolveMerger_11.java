
	private void setUpMergeWalk(TreeWalk treeWalk
			RevTree headTree
		treeWalk.addTree(baseTree == null ? new EmptyTreeIterator() : openTree(baseTree));
		treeWalk.setHead(treeWalk.addTree(headTree));
		treeWalk.addTree(mergeTree);
		int dciPos = treeWalk.addTree(buildIt);
		if (workingTreeIterator != null) {
			if (workingTreeIterator.eof()) {
				workingTreeIterator.reset();
			}
			treeWalk.addTree(workingTreeIterator);
			workingTreeIterator.setDirCacheIterator(treeWalk
		} else {
			treeWalk.setFilter(TreeFilter.ANY_DIFF);
		}
	}

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
			walk.swapTree(swapNth
			if(hasAttributeNodeProvider) {
				attributes[swapNth] = swapWalk.getAttributes(i);
			}
			i++;
		}
	}


	private boolean processRenames(RevTree baseTree
			RevTree headTree

		if(checkWorkTreeDirty(baseTree
			return false;
		}
		for(Entry<String
			reportRenameConflict(sourceRenameConflict.getValue()
		}

		for (Entry<String
			RenameType renameType = baseRename.getValue().getRenameType();
			RenameProcessingTreeWalk renameTw = new RenameProcessingTreeWalk(db
			DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder
			setUpMergeWalk(renameTw

			renameTw.setAttributesNodeProvider(tw.getAttributesNodeProvider());

			Attributes[] attributes = {NO_ATTRIBUTES
					NO_ATTRIBUTES};

			if (renameType.equals(RenameType.RENAME_BOTH_NO_CONFLICT)) {
				setUpRenameWalk(renameTw
			} else if (renameType.equals(RENAME_IN_OURS)) {
				setUpRenameWalk(renameTw
			} else if (renameType.equals(RENAME_IN_THEIRS)) {
				setUpRenameWalk(renameTw
			}

			renameTw.setPathName(baseRename.getValue().getTargetPath());
			tw = renameTw;
			boolean success = processEntry(renameTw.getTree(T_BASE
					renameTw.getTree(T_OURS
					renameTw.getTree(T_THEIRS
					renameTw.getTree(T_INDEX
					renameTw.getTreeCount() > T_FILE ? renameTw.getTree(T_FILE
							WorkingTreeIterator.class) : null
					attributes
			if(!success){
				return false;
			}
			updateWorkingTree(renameTw.getTree(T_OURS
		}
		return true;
	}

	private void updateWorkingTree(AbstractTreeIterator ours
			throws IOException {
		boolean hasWorkingTreeEntry =
				treeWalk.getTreeCount() > T_FILE && treeWalk.getRawMode(T_FILE) != 0;
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
		if (ignoreConflicts) {
			return;
		}
		if (renameEntry.getRenameConflict().equals(RENAME_BOTH_SIDES_CONFLICT)) {
			reportRenameRenameConflict(renameEntry
		} else if (renameEntry.getRenameConflict().equals(RENAME_DELETE_CONFLICT)) {
			reportRenameDeleteConflict(renameEntry
		}
	}


	private boolean checkWorkTreeDirty(RevTree baseTree
			throws IOException {
		Set<String> renamePaths = new HashSet<>();
		for (Entry<String
			if (rename.getValue().getRenameType().equals(RENAME_IN_THEIRS)) {
				renamePaths.add(rename.getKey());
			}
			renamePaths.add(rename.getValue().getTargetPath());
		}
		for (Entry<String
			renamePaths.addAll(rename.getValue().getTargetPaths());
		}
		if (renamePaths.isEmpty()) {
			return false;
		}
		TreeFilter renamePathsFilter = PathFilterGroup.createFromStrings(renamePaths);
		TreeWalk mergeTreeWalk = new TreeWalk(db

		DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder
		setUpMergeWalk(mergeTreeWalk
		mergeTreeWalk.setFilter(renamePathsFilter);
		mergeTreeWalk.setAttributesNodeProvider(tw.getAttributesNodeProvider());

		tw = mergeTreeWalk;
		while (mergeTreeWalk.next()) {
			if (!checkIndexAndWorkTree(mergeTreeWalk.getTree(T_INDEX
					workingTreeIterator != null ? mergeTreeWalk.getTree(T_FILE
							WorkingTreeIterator.class) : null)) {
				return true;
			}
		}
		return false;
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
				oursWalk != null ? oursWalk.getRawPath() : theirsWalk.getRawPath();
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


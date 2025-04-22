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

	private void recordStages(TreeWalk baseWalk
			throws IOException {
		if(ignoreConflicts){
			add(baseWalk.getRawPath()
			return;
		}

		add(baseWalk.getRawPath()
		add(oursWalk.getRawPath()
		DirCacheEntry theirsEntry = add(theirsWalk.getRawPath()
		MergeResult conflictResult = new MergeResult<>(Collections.emptyList());
		conflictResult.setContainsConflicts(true);
		unmergedPaths.add(oursWalk.getPathString());
		mergeResults.put(oursWalk.getPathString()
		unmergedPaths.add(theirsWalk.getPathString());
		mergeResults.put(theirsWalk.getPathString()
		if (nonTree(theirsEntry.getRawMode()) && theirsEntry != null) {
			Attributes[] attributes = { NO_ATTRIBUTES
					theirsWalk.getAttributes() };
			addToCheckout(theirsEntry.getPathString()
					attributes);
		}
	}

	private void reportRenameConflict(String originalPath
			throws IOException {
		recordStages(originalPath
		Map<Integer
		if(Objects.equals(renamePaths.get(T_OURS)
			return;
		}
		for (String renamePath : renamePaths.values()) {
			recordStages(renamePath
		}
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
			case RENAME_ADD_CONFLICT: {
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

	private void reportRenameAddConflict(int renameSide
			throws IOException {
		MergeResult conflictResult = new MergeResult<>(Collections.emptyList());
		conflictResult.setContainsConflicts(true);
		TreeWalk baseWalk = getRenameWalk(base
		TreeWalk oursWalk = getRenameWalk(ours
		TreeWalk theirsWalk = getRenameWalk(theirs
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

	private void recordStages(String path
			throws IOException {

		TreeWalk treeWalk = getRenameWalk(List.of(base
		if (ignoreConflicts) {
			add(treeWalk.getRawPath()
					DirCacheEntry.STAGE_0
			return;
		}

		add(treeWalk.getRawPath()
				DirCacheEntry.STAGE_1
		add(treeWalk.getRawPath()
				DirCacheEntry.STAGE_2
		DirCacheEntry theirsEntry = add(treeWalk.getRawPath()
				treeWalk.getTree(T_THEIRS
		MergeResult conflictResult = new MergeResult<>(Collections.emptyList());
		conflictResult.setContainsConflicts(true);
		unmergedPaths.add(treeWalk.getPathString());
		mergeResults.put(treeWalk.getPathString()

	}

	public class RenameResolver {

		Map of base paths to rename paths by tree
		Map<String

		Map<Integer

		Map<String
		Map<String


		boolean isRenameProcessing = false;

		public void addRenames(RevTree baseTree
				RevTree head
			RenameDetector renameDetector = new RenameDetector(reader
			List<DiffEntry> headRenames = computeRenames(renameDetector
			List<DiffEntry> mergeRenames = computeRenames(renameDetector
			for (DiffEntry entry : headRenames) {
				addRenameEntry(entry
			}
			for (DiffEntry entry : mergeRenames) {
				addRenameEntry(entry
			}

			detectRenameConflicts(headRenames

			renamePathsByTree.put(T_OURS
			renamePathsByTree.put(T_THEIRS
			for (Entry<String
				for (Entry<Integer
					renamePathsByTree.get(sideRename.getKey())
							.put(sideRename.getValue()
				}
			}
		}

		private void detectRenameConflicts(List<DiffEntry> headRenames
			Set<String> headDeletions = headRenames.stream().filter(x -> x.getChangeType().equals(ChangeType.DELETE)).map(x -> x.getOldPath()).collect(
					Collectors.toSet());
			Map<String
					x-> x));

			Set<String> mergeDeletions = mergeRenames.stream().filter(x -> x.getChangeType().equals(ChangeType.DELETE)).map(x -> x.getOldPath()).collect(
					Collectors.toSet());
			Map<String
					x-> x));
			Set<String> offSourceRenames = new HashSet<>();
			for (Entry<String
				Map<Integer
				if (renames.containsKey(T_OURS) && !renames.containsKey(T_THEIRS)) {
					offSourceRenames.addAll(detectRenameConflicts(baseRename.getKey()
				} else if(renames.containsKey(T_THEIRS) && !renames.containsKey(T_OURS)){
					offSourceRenames.addAll(detectRenameConflicts(baseRename.getKey()
				} else if (!renames.get(T_OURS).equals(renames.get(T_THEIRS))) {
					recordSourceConflict(baseRename.getKey()
				}
			}
			offSourceRenames.forEach(x -> baseRenamePaths.remove(x));

		}

		private Set<String> detectRenameConflicts(String sourcePath
			Set<String> offSourceRenames = new HashSet<>();
			if (otherSideDeletions.contains(sourcePath)) {
				recordSourceConflict(sourcePath
				recordTargetConflict(sideTargetPath
			}
			if(!otherSideDiffsByTargetPath.containsKey(sideTargetPath)){
				return Set.of();
			}

			DiffEntry otherSideTargetDiffEntry = otherSideDiffsByTargetPath.get(sideTargetPath);
			if (!otherSideTargetDiffEntry.getOldPath().equals(sourcePath)) {
					if(!otherSideDiffsByTargetPath.get(
							sideTargetPath).getChangeType().equals(ChangeType.RENAME)) {
						recordSourceConflict(sourcePath
						recordTargetConflict(sideTargetPath
						offSourceRenames.add(sourcePath);
					} else if(!otherSideTargetDiffEntry.getOldPath().equals(sideTargetPath)){
						recordSourceConflict(sourcePath
						recordSourceConflict(otherSideTargetDiffEntry.getOldPath()
						recordTargetConflict(sideTargetPath
						offSourceRenames.add(sourcePath);
						offSourceRenames.add(otherSideTargetDiffEntry.getOldPath());
					}
			}
			return offSourceRenames;
		}

		private void addRenameEntry(DiffEntry entry
			if (!entry.getChangeType().equals(ChangeType.RENAME) || entry.getOldPath()
					.equals(entry.getNewPath())) {
				return;
			}
			if (FileMode.TREE.equals(entry.getNewMode()) || FileMode.TREE.equals(entry.getOldMode())) {
				return;
			}
			if (!baseRenamePaths.containsKey(entry.getOldPath())) {
				baseRenamePaths.put(entry.getOldPath()
			}

			baseRenamePaths.get(entry.getOldPath()).put(entrySide
		}

		private void recordSourceConflict(String path
			if(!conflictingSourceRenamePath.containsKey(renameType)) {
				conflictingSourceRenamePath.put(path
			}
		}

		private boolean isSourceConflict(AbstractTreeIterator base) {
			return base != null && renameResolver.conflictingSourceRenamePath.containsKey(
					base.getEntryPathString());
		}

		private boolean isTargetConflict(AbstractTreeIterator side){
			return side != null && renameResolver.conflictingTargetRenamePath.containsKey(side.getEntryPathString());
		}

		private boolean isReportedRenameConflict(Entry<String
			if (renameResolver.conflictingSourceRenamePath.containsKey(renameEntry.getKey())
					|| renameResolver.conflictingTargetRenamePath.containsKey(renameEntry.getKey())) {
				return true;
			}
			for (Entry<Integer
				if (renameResolver.conflictingSourceRenamePath.containsKey(renameEntry.getKey())
						|| renameResolver.conflictingTargetRenamePath.containsKey(targetRename.getValue())) {
					return true;
				}
			}
			return false;
		}



		private void recordTargetConflict(String path
			if(!conflictingTargetRenamePath.containsKey(renameType)) {
				conflictingTargetRenamePath.put(path
			}
		}


		private List<DiffEntry> computeRenames(RenameDetector renameDetector
				RevTree otherTree)
				throws IOException {
			TreeWalk tw = new NameConflictTreeWalk(db
			tw.reset();
			tw.addTree(baseTree);
			tw.addTree(otherTree);
			tw.setFilter(TreeFilter.ANY_DIFF);

			renameDetector.reset();
			renameDetector.setBreakScore(100);
			renameDetector.addAll(DiffEntry.scan(tw
			try {
				return renameDetector.compute(reader
			} catch (CanceledException ex) {
				throw new IOException(ex);
			}
		}


		private boolean isBaseRename(AbstractTreeIterator base) {
			return base != null && baseRenamePaths.containsKey(base.getEntryPathString());
		}

		private boolean isBaseRename(String path) {
			return baseRenamePaths.containsKey(path);
		}

		public boolean isRenameFromBase(int nthA
			return side != null && isRenameFromBase(nthA
		}

		public boolean isRenameFromBase(int nthA
			return (renamePathsByTree.containsKey(nthA) && renamePathsByTree.get(nthA)
					.containsKey(path));
		}

		public String getOriginalByRename(int nthA
			return side != null  && renamePathsByTree.containsKey(nthA) ? renamePathsByTree.get(nthA).get(side.getEntryPathString()): null;
		}

		public boolean isRenameEntry(CanonicalTreeParser base
				CanonicalTreeParser ours

			return isBaseRename(base) || isRenameFromBase(T_OURS
					theirs);
		}


		public AbstractTreeIterator[] swapRenames(AbstractTreeIterator base
				AbstractTreeIterator ours
				throws IOException {

			AbstractTreeIterator[] canonicalTreeParsers = {null
			if(isTree(base) || isTree(ours) ||  isTree(theirs)){
				return new AbstractTreeIterator[]{base
			}
			if (isBaseRename(base)) {
				Map<Integer
				if (paths.containsKey(T_OURS) && !paths.containsKey(T_THEIRS)){
					} else
						return new AbstractTreeIterator[]{null
					}
				} else if (paths.containsKey(T_THEIRS) && !paths.containsKey(T_OURS)) {
						recordSourceConflict(base.getEntryPathString()
					}
						return new AbstractTreeIterator[]{null
					}
				} else if (paths.containsKey(T_OURS) && paths.containsKey(T_THEIRS) && !paths.get(T_OURS)
						.equals(paths.get(T_THEIRS))) {
					recordSourceConflict(base.getEntryPathString()
					return new AbstractTreeIterator[]{null
				} else if (!isBaseRename(paths.get(T_OURS))) {
					return new AbstractTreeIterator[]{null
				}
				return new AbstractTreeIterator[]{null
			}
			else if (isRenameFromBase(T_OURS

				return new AbstractTreeIterator[]{null
			}
			else if (isRenameFromBase(T_THEIRS
				return new AbstractTreeIterator[]{null
			}
			return canonicalTreeParsers;
		}

		private String getDir(String path){
			int endDir = path.lastIndexOf("/");
			return path.substring(0
		}
	}


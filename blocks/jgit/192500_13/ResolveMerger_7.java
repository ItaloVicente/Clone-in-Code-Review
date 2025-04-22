
	private DirCacheEntry addToIndexAndCheckout(CanonicalTreeParser p
		DirCacheEntry e = add(tw.getRawPath()
				DirCacheEntry.STAGE_0
		if (e != null) {
			addToCheckout(tw.getPathString()
		}
		return e;
	}

	private DirCacheEntry add(byte[] path
			Instant lastMod
		if (!fileMode.equals(FileMode.TREE)) {
			DirCacheEntry e = new DirCacheEntry(path
			e.setFileMode(fileMode);
			e.setObjectId(objectId);
			e.setLastModified(lastMod);
			e.setLength(len);
			builder.add(e);
			return e;
		}
		return null;
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

	public enum RenameType{NO_RENAME

	private boolean processRenames(RevTree baseTree
			RevTree headTree
		for(Entry<String
			reportRenameConflict(sourceRenameConflict.getKey()
		}
		this.renameResolver.isRenameProcessing = true;
		RenameType renameType = RenameType.NO_RENAME;
		for (Entry<String

			if (renameResolver.conflictingSourceRenamePath.containsKey(baseRename.getKey())) {
				continue;
			}
			Map<Integer
			if (baseRenames.size() > 1) {
				renameType = !baseRenames.get(T_OURS).equals(baseRenames.get(T_THEIRS)) ? RENAME_BOTH_SIDES_CONFLICT
						: RENAME_BOTH_NO_CONFLICT;
			} else {
				renameType = baseRenames.containsKey(T_OURS) ? RENAME_IN_OURS : RENAME_IN_THEIRS;
			}

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
					NO_ATTRIBUTES};
			String renamePath =
					baseRenames.containsKey(T_OURS) ? baseRenames.get(T_OURS) : baseRenames.get(T_THEIRS);

			if (renameType.equals(RENAME_BOTH_NO_CONFLICT)) {
				setUpRenameWalk(indexTw
			} else if (renameType.equals(RENAME_IN_OURS)) {
				setUpRenameWalk(indexTw
			} else if (renameType.equals(RENAME_IN_THEIRS)) {
				setUpRenameWalk(indexTw
			}

			indexTw.setPathName(renamePath);
			tw = indexTw;
			boolean success = processEntry(indexTw.getTree(T_BASE
					indexTw.getTree(T_OURS
					indexTw.getTree(T_THEIRS
					indexTw.getTree(T_INDEX
					tw.getTreeCount() > T_FILE ? indexTw.getTree(T_FILE
							WorkingTreeIterator.class) : null
					attributes
			cleanUpWorkingTree(indexTw.getTree(T_OURS
			if(!success){
				return false;
			}
		}
		return true;
	}
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
				int renameSide = renamePaths.containsKey(T_OURS) ? T_OURS : T_THEIRS;
				String targetPath = renamePaths.get(renameSide);
				reportRenameAddConflict(renameSide
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
		Map<String
		Map<String
		Map<String
		Map of base paths to rename paths by tree
		Map<AnyObjectId
		List<DiffEntry> headRenames;
		List<DiffEntry> mergeRenames;

		Map<Integer

		boolean isRenameProcessing = false;

		public void addRenames(RevTree baseTree
				RevTree head
			RenameDetector renameDetector = new RenameDetector(reader
			headRenames = computeRenames(renameDetector
			mergeRenames = computeRenames(renameDetector
			renamePathsByTree.put(T_OURS
			renamePathsByTree.put(T_THEIRS
			for (DiffEntry entry : headRenames) {
				addRenameEntry(entry
			}
			for (DiffEntry entry : mergeRenames) {
				addRenameEntry(entry
			}
			detectRenameConflicts();
		}

		private void detectRenameConflicts(){
			Set<String> headDeletions = headRenames.stream().filter(x -> x.getChangeType().equals(ChangeType.DELETE)).map(x -> x.getOldPath()).collect(
					Collectors.toSet());
			Map<String
					x-> x));

			Set<String> mergeDeletions = mergeRenames.stream().filter(x -> x.getChangeType().equals(ChangeType.DELETE)).map(x -> x.getOldPath()).collect(
					Collectors.toSet());
			Map<String
					x-> x));
			for (Entry<String
				Map<Integer
				if (renames.containsKey(T_OURS) && !renames.containsKey(T_THEIRS)) {
					detectRenameConflicts(baseRename.getKey()
				} else if(renames.containsKey(T_THEIRS) && !renames.containsKey(T_OURS)){
					detectRenameConflicts(baseRename.getKey()
				}
			}

		}

		private void detectRenameConflicts(String sourcePath
				if(otherSideDeletions.contains(sourcePath)){
					recordSourceConflict(sourcePath
					recordTargetConflict(sideTargetPath
				}
				if(otherSideDiffsByTargetPath.containsKey(sideTargetPath) && !otherSideDiffsByTargetPath.get(sideTargetPath).getOldPath().equals(sourcePath)) {
					baseRenamePaths.remove(sourcePath);
					renamePathsByTree.get(renameSide).remove(sideTargetPath);
					renamePathsToBase.remove(sideTargetPath);
			}
		}

		private void addRenameEntry(DiffEntry entry
			if (!entry.getChangeType().equals(ChangeType.RENAME) || entry.getOldPath()
					.equals(entry.getNewPath())) {
				return;
			}
			if (FileMode.TREE.equals(entry.getNewMode()) || FileMode.TREE.equals(entry.getOldMode())) {
				return;
			}

			if (renamePathsToBase.containsKey(entry.getNewPath()) && !renamePathsToBase.get(
					entry.getNewPath()).equals(entry.getOldPath())) {
				String previousRename = renamePathsToBase.get(entry.getNewPath());
				baseRenamePaths.remove(previousRename);
				renamePathsByTree.get(otherSide).remove(entry.getNewPath());
				renamePathsToBase.remove(entry.getNewPath());
				return;
			}
			if (!baseRenamePaths.containsKey(entry.getOldPath())) {
				baseRenamePaths.put(entry.getOldPath()
			}
			if (baseRenamePaths.get(entry.getOldPath()).size() > 0 && !baseRenamePaths.get(entry.getOldPath()).get(otherSide).equals(entry.getNewPath())) {
				recordSourceConflict(entry.getOldPath()
			}
			renamePathsToBase.put(entry.getNewPath()
			baseRenamePaths.get(entry.getOldPath()).put(entrySide
			renamePathsByTree.get(entrySide).put(entry.getNewPath()
		}

		private CanonicalTreeParser treeParserFor(int nth
			String renameTree =  getDir(path);
			Optional<DiffEntry> treeNode  = (nth==T_OURS? headRenames: mergeRenames).stream().filter(x -> x.getNewPath().equals(renameTree)).findFirst();
			return parserFor(treeNode.get().getNewId().toObjectId());
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


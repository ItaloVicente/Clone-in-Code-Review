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

	private void reportConflict(TreeWalk baseWalk
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
		reportConflict(originalPath
		Map<Integer
		if(Objects.equals(renamePaths.get(T_OURS)
			return;
		}
		for (String renamePath : renamePaths.values()) {
			reportConflict(renamePath
		}
	}

	private void reportConflict(String path
			throws IOException {

		TreeWalk treeWalk = getRenameWalk(List.of(base
		if (ignoreConflicts) {
			add(treeWalk.getRawPath()
					DirCacheEntry.STAGE_0
			return;
		}

		remove(treeWalk.getRawPath()
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

		if (theirsEntry != null && nonTree(theirsEntry.getRawMode()) && isRenamePath && (
				renameType.equals(RENAME_BOTH_SIDES_CONFLICT) || renameType.equals(
						RENAME_OURS_ADD_THEIRS_CONFLICT)|| renameType.equals(RENAME_THEIRS_REMOVE_OURS_CONFLICT))) {
			Attributes[] attributes = {NO_ATTRIBUTES
					treeWalk.getAttributes()};
			addToCheckout(theirsEntry.getPathString()
					attributes);
		}
	}

	public class RenameResolver {

		Map of base paths to rename paths by tree
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
				recordConflict(entry.getOldPath()
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

		private void recordConflict(String path
			if(!conflictingRenamePath.containsKey(renameType)) {
				conflictingRenamePath.put(path
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
					if(theirs == null){
						recordConflict(base.getEntryPathString()
						return new AbstractTreeIterator[]{null
					} else if(!isBaseRename(paths.get(T_OURS))) {
						return new AbstractTreeIterator[]{null
					}
				} else if (paths.containsKey(T_THEIRS) && !paths.containsKey(T_OURS)) {
					if (ours == null) {
						recordConflict(base.getEntryPathString()
					} else if (!isBaseRename(paths.get(T_THEIRS))) {
						return new AbstractTreeIterator[]{null
					}
				} else if (paths.containsKey(T_OURS) && paths.containsKey(T_THEIRS) && !paths.get(T_OURS)
						.equals(paths.get(T_THEIRS))) {
					recordConflict(base.getEntryPathString()
					return new AbstractTreeIterator[]{null
				} else if (!isBaseRename(paths.get(T_OURS))) {
					return new AbstractTreeIterator[]{null
				}
				return new AbstractTreeIterator[]{null
			}
			else if (isRenameFromBase(T_OURS

				recordConflict(getOriginalByRename(T_OURS
				return new AbstractTreeIterator[]{null
			}
			else if (isRenameFromBase(T_THEIRS
				recordConflict(getOriginalByRename(T_THEIRS
				return new AbstractTreeIterator[]{null
			}
			return canonicalTreeParsers;
		}

		private String getDir(String path){
			int endDir = path.lastIndexOf("/");
			return path.substring(0
		}
	}


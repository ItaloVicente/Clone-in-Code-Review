
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

		if(nonTree(oursEntry.getRawMode()) && (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) != 0) && !oursEntry.getPathString().equals(tw.getPathString())) {
			addDeletion(oursEntry.getPathString()
		}
		return addToIndexAndCheckout(ours
	}

	private TreeWalk getRenameWalk(RevTree renameTree
		TreeWalk renameWalk = new TreeWalk(db
		renameWalk.setAttributesNodeProvider(tw.getAttributesNodeProvider());
		renameWalk.addTree(renameTree);
		renameWalk.addTree(new DirCacheBuildIterator(builder
		TreeWalk.walkToPath(renameWalk
		return renameWalk;
	}

	private void setUpRenameWalk(RenameProcessingTreeWalk walk
			throws IOException {

		TreeWalk.walkToPath(walk
		boolean hasAttributeNodeProvider = walk
				.getAttributesNodeProvider() != null;
		if (hasAttributeNodeProvider) {
			attributes[T_BASE] = walk.getAttributes(T_BASE);
			attributes[T_OURS] = walk.getAttributes(T_OURS);
			attributes[T_THEIRS] = walk.getAttributes(T_THEIRS);
		}
		TreeWalk swapWalk = getRenameWalk(swapTree
		walk.swapRenameTree(swapTreeNTh
		attributes[swapTreeNTh] = swapWalk.getAttributes(0);
	}

	public enum RenameType{NO_RENAME

	private void processRenames(RevTree baseTree
			RevTree headTree
		this.renameResolver.isRenameProcessing = true;
		RenameType renameType = RenameType.NO_RENAME;
		for (Entry<String
			Map<Integer
			if (baseRenames.size() > 1) {
				renameType = !baseRenames.get(T_OURS).equals(baseRenames.get(T_THEIRS)) ? RENAME_CONFLICT
						: RENAME_BOTH_NO_CONFLICT;
			} else {
				renameType = baseRenames.containsKey(T_OURS) ? RENAME_IN_OURS : RENAME_IN_THEIRS;
			}
			if (renameType.equals(RENAME_CONFLICT)) {
				TreeWalk baseWalk = getRenameWalk(baseTree
				TreeWalk oursWalk = getRenameWalk(headTree
				TreeWalk theirsWalk = getRenameWalk(mergeTree
				reportConflict(baseWalk
				return;
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
			processEntry(indexTw.getTree(T_BASE
					indexTw.getTree(T_OURS
					indexTw.getTree(T_THEIRS
					indexTw.getTree(T_INDEX
					tw.getTreeCount() > T_FILE ? indexTw.getTree(T_FILE
							WorkingTreeIterator.class) : null
					attributes
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

	public class RenameResolver {

		Map of base paths to rename paths by tree
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
				if (!entry.getChangeType().equals(ChangeType.RENAME)) {
					continue;
				}
				if (!baseRenamePaths.containsKey(entry.getOldPath())) {
					baseRenamePaths.put(entry.getOldPath()
				}
				baseRenamePaths.get(entry.getOldPath()).put(T_OURS
				renamePathsByTree.get(T_OURS).put(entry.getNewPath()
			}
			for (DiffEntry entry : mergeRenames) {
				if (!entry.getChangeType().equals(ChangeType.RENAME)) {
					continue;
				}
				if (!baseRenamePaths.containsKey(entry.getOldPath())) {
					baseRenamePaths.put(entry.getOldPath()
				}
				baseRenamePaths.get(entry.getOldPath()).put(T_THEIRS
				renamePathsByTree.get(T_THEIRS).put(entry.getNewPath()
			}
		}

		private CanonicalTreeParser treeParserFor(int nth
			String renameTree =  getDir(path);
			Optional<DiffEntry> treeNode  = (nth==T_OURS? headRenames: mergeRenames).stream().filter(x -> x.getNewPath().equals(renameTree)).findFirst();
			return parserFor(treeNode.get().getNewId().toObjectId());
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

		public boolean isRenameFromBase(int nthA
			return side != null && (renamePathsByTree.containsKey(nthA) && renamePathsByTree.get(nthA)
					.containsKey(side.getEntryPathString()));
		}

		public boolean isRenameEntry(CanonicalTreeParser base
				CanonicalTreeParser ours

			return isBaseRename(base) || isRenameFromBase(T_OURS
					theirs);
		}

		private String getDir(String path){
			int endDir = path.lastIndexOf("/");
			return path.substring(0
		}
	}


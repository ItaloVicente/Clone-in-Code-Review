
	private DirCacheEntry addToIndex(CanonicalTreeParser p
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

		if(!renameResolver.isRenameProcessing){
			return keep(oursEntry);
		}
		if(nonTree(oursEntry.getRawMode()) && (tw.getTreeCount() > T_FILE && tw.getRawMode(T_FILE) != 0)) {
			addDeletion(oursEntry.getPathString()
		}
		return addToIndex(ours
	}

	private void processRenames(RevTree baseTree
			RevTree headTree
		for (Entry<String
			Map<Integer
			if (baseRenames.size() > 1) {
				reportConflict();
			}
			boolean isRenameInOurs = baseRenames.containsKey(T_OURS);

			RenameProcessingTreeWalk indexTw = new RenameProcessingTreeWalk(db
			tw = indexTw;
			DirCacheBuildIterator buildIt = new DirCacheBuildIterator(builder);
			int dciPos = indexTw.addTree(buildIt);
			if (workingTreeIterator != null) {
				indexTw.addTree(workingTreeIterator);
				workingTreeIterator.setDirCacheIterator(indexTw
			} else {
				indexTw.setFilter(TreeFilter.ANY_DIFF);
			}
			indexTw.setAttributesNodeProvider(tw.getAttributesNodeProvider());
			Attributes[] attributes = {NO_ATTRIBUTES
					NO_ATTRIBUTES};
			TreeWalk.walkToPath(indexTw
			boolean hasWorkingTreeIterator = tw.getTreeCount() > T_FILE;
			CanonicalTreeParser ours;
			CanonicalTreeParser theirs;
			String renamePath  = baseRenames.containsKey(T_OURS) ? baseRenames.get(T_OURS) : baseRenames.get(T_THEIRS);
			if(isRenameInOurs){
				ours = parserFor(headTree);
				if(ours.findFile(renamePath)) {
					indexTw.swapRenameTree(T_OURS
				}
			} else {
				theirs = parserFor(mergeTree);
				if (theirs.findFile(renamePath)) {
					indexTw.swapRenameTree(T_THEIRS
				}
			}
			indexTw.setPathName(renamePath);
			boolean hasAttributeNodeProvider = indexTw
					.getAttributesNodeProvider() != null;
			if (hasAttributeNodeProvider) {
				attributes[T_BASE] = indexTw.getAttributes(T_BASE);
				attributes[T_OURS] = indexTw.getAttributes(T_OURS);
				attributes[T_THEIRS] = indexTw.getAttributes(T_THEIRS);
			}
			processEntry(indexTw.getTree(T_BASE
					indexTw.getTree(T_OURS
					indexTw.getTree(T_THEIRS
					indexTw.getTree(T_INDEX
					hasWorkingTreeIterator ? indexTw.getTree(T_FILE
							WorkingTreeIterator.class) : null
		}
	}

	private CanonicalTreeParser parserFor(AnyObjectId id)
			throws IncorrectObjectTypeException
		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(reader
		return p;
	}

	private void reportConflict(){
	}

	public class RenameResolver {

		Map of base paths to rename paths by tree
		Map<String


		Map of base paths to rename paths by tree
		Map<AnyObjectId

		Map<Integer

		boolean isRenameProcessing = false;

		public void addRenames(RevTree baseTree
				RevTree head
			RenameDetector renameDetector = new RenameDetector(reader
			List<DiffEntry> headRenames = computeRenames(renameDetector
			List<DiffEntry> mergeRenames = computeRenames(renameDetector
			renamePathsByTree.put(T_OURS
			renamePathsByTree.put(T_THEIRS
			for (DiffEntry entry : headRenames) {
				if (!baseRenamePaths.containsKey(entry.getOldPath())) {
					baseRenamePaths.put(entry.getOldPath()
				}
				baseRenamePaths.get(entry.getOldPath()).put(T_OURS
				renamePathsByTree.get(T_OURS).put(entry.getNewPath()
			}
			for (DiffEntry entry : mergeRenames) {
				if (!baseRenamePaths.containsKey(entry.getOldPath())) {
					baseRenamePaths.put(entry.getOldPath()
				}
				baseRenamePaths.get(entry.getOldPath()).put(T_THEIRS
				renamePathsByTree.get(T_THEIRS).put(entry.getNewPath()
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
			renameDetector.addAll(DiffEntry.scan(tw
			try {
				return renameDetector.compute(reader
			} catch (CanceledException ex) {
				throw new IOException(ex);
			}
		}


		private boolean isBaseRename(AbstractTreeIterator base) {
			return baseRenamePaths.containsKey(base.getEntryPathString());
		}

		public boolean isRenameFromBase(int nthA
			return renamePathsByTree.containsKey(nthA) && renamePathsByTree.get(nthA)
					.containsKey(side.getEntryPathString());
		}

		public boolean isRenameEntry(CanonicalTreeParser base
				CanonicalTreeParser ours

			return isBaseRename(base) || isRenameFromBase(T_OURS
					theirs);
		}
	}


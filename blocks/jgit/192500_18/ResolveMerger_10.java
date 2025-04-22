		if(tw instanceof RenameProcessingTreeWalk && ((RenameProcessingTreeWalk) tw).isTerminated()){
			cleanUp();
			return false;
		}


		if (detectRenames) {
			boolean success = processRenames(baseTree
			if (!success) {
				cleanUp();
				return false;
			}
		}

		return true;
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
			walk.swapRenameTree(swapNth
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
					NO_ATTRIBUTES};

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
			if(!success){
				return false;
			}
			updateWorkingTree(indexTw.getTree(T_OURS
		}

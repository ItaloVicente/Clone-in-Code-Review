			CanonicalTreeParser base =
					treeWalk.getTree(T_BASE
			CanonicalTreeParser ours =
					treeWalk.getTree(T_OURS
			CanonicalTreeParser theirs =
					treeWalk.getTree(T_THEIRS
			DirCacheBuildIterator index =
					treeWalk.getTree(T_INDEX
			WorkingTreeIterator work = hasWorkingTreeIterator ? treeWalk.getTree(T_FILE
					WorkingTreeIterator.class) : null;
			if(renameResolver.isRenameEntry(base
				AbstractTreeIterator[] treesWithZeroedRenames = renameResolver.swapRenames(base
				if(Arrays.equals(treesWithZeroedRenames
					continue;
				}
				tw.swapRenames(treesWithZeroedRenames);
			}
			boolean success = processEntry(

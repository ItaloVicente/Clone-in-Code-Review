				if (merger instanceof RecursiveMerger) {
					RecursiveMerger recursiveMerger = (RecursiveMerger) merger;
					recursiveMerger.setCommitNames(new String[] { "BASE"
							"HEAD"
					recursiveMerger
							.setWorkingTreeIterator(new FileTreeIterator(repo));
					noProblems = merger.merge(headCommit
					lowLevelResults = recursiveMerger.getMergeResults();
					failingPaths = recursiveMerger.getFailingPaths();
					unmergedPaths = recursiveMerger.getUnmergedPaths();
				} else if (merger instanceof ResolveMerger) {

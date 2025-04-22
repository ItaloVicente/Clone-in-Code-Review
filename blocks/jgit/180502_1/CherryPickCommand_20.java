				Merger merger = strategy.newMerger(repo);
				merger.setProgressMonitor(monitor);
				boolean noProblems;
				Map<String
				List<String> unmergedPaths = null;
				if (merger instanceof ResolveMerger) {
					ResolveMerger resolveMerger = (ResolveMerger) merger;
					resolveMerger.setContentMergeStrategy(contentStrategy);
					resolveMerger.setCommitNames(
							new String[] { "BASE"
					resolveMerger
							.setWorkingTreeIterator(new FileTreeIterator(repo));
					resolveMerger.setBase(srcParent.getTree());
					noProblems = merger.merge(newHead
					failingPaths = resolveMerger.getFailingPaths();
					unmergedPaths = resolveMerger.getUnmergedPaths();
					if (!resolveMerger.getModifiedFiles().isEmpty()) {

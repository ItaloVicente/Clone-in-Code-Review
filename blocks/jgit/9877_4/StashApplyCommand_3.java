			ObjectId headTree = repo.resolve(Constants.HEAD + "^{tree}");
			ObjectId stashIndexCommit = revWalk.parseCommit(stashCommit
					.getParent(1));
			ObjectId stashHeadCommit = stashCommit.getParent(0);

			ResolveMerger merger = (ResolveMerger) MergeStrategy.RESOLVE
					.newMerger(repo);
			merger.setCommitNames(new String[] { "stashed HEAD"
					"stash" });
			merger.setBase(stashHeadCommit);
			merger.setWorkingTreeIterator(new FileTreeIterator(repo));
			if (merger.merge(headCommit
				DirCache dc = repo.lockDirCache();
				DirCacheCheckout dco = new DirCacheCheckout(repo
						dc
				dco.setFailOnConflict(true);
				if (applyIndex) {
					ResolveMerger ixMerger = (ResolveMerger) MergeStrategy.RESOLVE
							.newMerger(repo
					ixMerger.setCommitNames(new String[] { "stashed HEAD"
							"HEAD"
					boolean ok = ixMerger.merge(headCommit
					if (ok) {
						resetIndex(revWalk
								.parseTree(ixMerger.getResultTreeId()));
					} else {
						throw new StashApplyFailureException(
								JGitText.get().stashApplyConflict);
					}
				}
			} else {
				throw new StashApplyFailureException(
						JGitText.get().stashApplyConflict);
			}
			return stashId;

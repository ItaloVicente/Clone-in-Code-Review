
				if (untrackedCommit != null) {
					ResolveMerger untrackedMerger = (ResolveMerger) strategy
							.newMerger(repo
					untrackedMerger.setCommitNames(new String[] {
							"stashed HEAD"
					untrackedMerger.setBase(stashHeadCommit);
					boolean ok = untrackedMerger.merge(headCommit
							untrackedCommit);
					if (ok) {
						try {
							RevTree untrackedTree = revWalk
									.parseTree(untrackedMerger
											.getResultTreeId());
							resetUntracked(untrackedTree);

						} catch (CheckoutConflictException e) {
							throw new StashApplyFailureException(
									JGitText.get().stashApplyConflict);
						}
					} else {
						throw new StashApplyFailureException(
								JGitText.get().stashApplyConflict);
					}
				}


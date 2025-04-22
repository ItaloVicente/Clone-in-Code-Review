					HashMap<Repository, Tree> treeMap = new HashMap<Repository, Tree>();
					try {
						if (!prepareTrees(filesToCommit, treeMap, actMonitor)) {
							for (Repository repo : treeMap.keySet())
								repo.getIndex().read();
							return;
						}
					} catch (IOException e) {
						throw new TeamException(
								CoreText.CommitOperation_errorPreparingTrees, e);
					}

					try {
						doCommits(message, treeMap);
						actMonitor.worked(filesToCommit.length);
					} catch (IOException e) {
						throw new TeamException(
								CoreText.CommitOperation_errorCommittingChanges,
								e);
					}

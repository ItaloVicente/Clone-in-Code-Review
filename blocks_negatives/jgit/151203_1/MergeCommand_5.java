				} else {
					if (failingPaths != null) {
						repo.writeMergeCommitMsg(null);
						repo.writeMergeHeads(null);
						return new MergeResult(null, merger.getBaseCommitId(),
								new ObjectId[] {
										headCommit.getId(), srcCommit.getId() },
								MergeStatus.FAILED, mergeStrategy,
								lowLevelResults, failingPaths, null);
					} else {
						String mergeMessageWithConflicts = new MergeMessageFormatter()
								.formatWithConflicts(mergeMessage,
										unmergedPaths);
						repo.writeMergeCommitMsg(mergeMessageWithConflicts);
						return new MergeResult(null, merger.getBaseCommitId(),
								new ObjectId[] { headCommit.getId(),
										srcCommit.getId() },
								MergeStatus.CONFLICTING, mergeStrategy,
								lowLevelResults, null);
					}

				if (failingPaths != null) {
					repo.writeMergeCommitMsg(null);
					repo.writeMergeHeads(null);
					return new MergeResult(null
							new ObjectId[] { headCommit.getId()
									srcCommit.getId() }
							MergeStatus.FAILED
							failingPaths
				}
				String mergeMessageWithConflicts = new MergeMessageFormatter()
						.formatWithConflicts(mergeMessage
				repo.writeMergeCommitMsg(mergeMessageWithConflicts);
				return new MergeResult(null
						new ObjectId[] { headCommit.getId()
								srcCommit.getId() }
						MergeStatus.CONFLICTING
						null);

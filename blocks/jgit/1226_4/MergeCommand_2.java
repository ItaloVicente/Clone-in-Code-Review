					if (failingPaths != null) {
						repo.writeMergeCommitMsg(null);
						repo.writeMergeHeads(null);
						return new MergeResult(null
								merger.getBaseCommit(0
								new ObjectId[] {
										headCommit.getId()
								MergeStatus.FAILED
								lowLevelResults
					} else
						return new MergeResult(null
								merger.getBaseCommit(0
								new ObjectId[] { headCommit.getId()
										srcCommit.getId() }
								MergeStatus.CONFLICTING
								lowLevelResults

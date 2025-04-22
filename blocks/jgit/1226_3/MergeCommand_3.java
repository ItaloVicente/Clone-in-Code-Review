					if (failingPathes!=null) {
						repo.writeMergeCommitMsg(null);
						repo.writeMergeHeads(null);
						return new MergeResult(null
								new ObjectId[] {
										headCommit.getId()
								MergeStatus.FAILED
								lowLevelResults
					} else
						return new MergeResult(null
								headCommit.getId()
								MergeStatus.CONFLICTING
								lowLevelResults

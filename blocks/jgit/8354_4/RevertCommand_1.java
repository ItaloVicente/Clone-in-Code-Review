					else
						failingResult = new MergeResult(null
								merger.getBaseCommit(0
								new ObjectId[] { headCommit.getId()
										srcParent.getId() }
								MergeStatus.CONFLICTING
								merger.getMergeResults()
					if (!merger.failed() && !unmergedPaths.isEmpty()) {
						repo.writeRevertHead(srcCommit.getId());
						repo.writeMergeCommitMsg(newMessage);
					}

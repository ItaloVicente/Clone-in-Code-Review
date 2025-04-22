					unmergedPaths = merger.getUnmergedPaths();
					Map<String, MergeFailureReason> failingPaths = merger
							.getFailingPaths();
					if (failingPaths != null)
						failingResult = new MergeResult(null,
								merger.getBaseCommit(0, 1),
								new ObjectId[] { headCommit.getId(),
										srcParent.getId() },
								MergeStatus.FAILED, MergeStrategy.RESOLVE,
								merger.getMergeResults(), failingPaths, null);
					else
						failingResult = new MergeResult(null,
								merger.getBaseCommit(0, 1),
								new ObjectId[] { headCommit.getId(),
										srcParent.getId() },
								MergeStatus.CONFLICTING, MergeStrategy.RESOLVE,
								merger.getMergeResults(), failingPaths, null);
					if (!merger.failed() && !unmergedPaths.isEmpty()) {
						String message = new MergeMessageFormatter()
						.formatWithConflicts(newMessage,
								merger.getUnmergedPaths());
						repo.writeRevertHead(srcCommit.getId());
						repo.writeMergeCommitMsg(message);
					}
					return null;


					String message = new MergeMessageFormatter()
							.formatWithConflicts(srcCommit.getFullMessage()
									merger.getUnmergedPaths());

					repo.writeCherryPickHead(srcCommit.getId());
					repo.writeMergeCommitMsg(message);


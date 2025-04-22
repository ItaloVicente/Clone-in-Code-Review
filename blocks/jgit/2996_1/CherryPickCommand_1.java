
					String message = new MergeMessageFormatter()
							.addConflictsSection(srcCommit.getFullMessage()
									merger.getUnmergedPaths());

					repo.writeCherryPickHead(srcCommit.getId());
					repo.writeMergeCommitMsg(message);


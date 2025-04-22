					if (merger.failed())
						return new CherryPickResult(merger.getFailingPaths());


					String message = new MergeMessageFormatter()
							.formatWithConflicts(newMessage
									merger.getUnmergedPaths());

					repo.writeRevertHead(srcCommit.getId());
					repo.writeMergeCommitMsg(message);

					return CherryPickResult.CONFLICT;

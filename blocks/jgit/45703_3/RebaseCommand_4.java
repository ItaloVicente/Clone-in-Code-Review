						pickCommand.setMainlineParentNumber(1);
						pickCommand.setNoCommit(true);
						writeMergeInfo(commitToPick
					}
					CherryPickResult cherryPickResult = pickCommand.call();
					switch (cherryPickResult.getStatus()) {
					case FAILED:
						if (operation == Operation.BEGIN)
							return abort(RebaseResult.failed(
									cherryPickResult.getFailingPaths()));
						else
							return stop(commitToPick
					case CONFLICTING:
						return stop(commitToPick
					case OK:
						if (isMerge) {
							CommitCommand commit = git.commit();
							commit.setAuthor(commitToPick.getAuthorIdent());
									+ commitToPick.getShortMessage());
							newHead = commit.call();
						} else
							newHead = cherryPickResult.getNewHead();
						break;
					}
				} else {
					MergeCommand merge = git.merge()
							.setFastForward(MergeCommand.FastForwardMode.NO_FF)
							.setCommit(false);
					for (int i = 1; i < commitToPick.getParentCount(); i++)
						merge.include(newParents.get(i));
					MergeResult mergeResult = merge.call();
					if (mergeResult.getMergeStatus().isSuccessful()) {
						CommitCommand commit = git.commit();

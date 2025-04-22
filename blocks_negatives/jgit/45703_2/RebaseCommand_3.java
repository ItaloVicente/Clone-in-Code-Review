					} else
						newHead = cherryPickResult.getNewHead();
					break;
				}
			} else {
				MergeCommand merge = new Git(repo).merge()
						.setFastForward(MergeCommand.FastForwardMode.NO_FF)
						.setCommit(false);
				for (int i = 1; i < commitToPick.getParentCount(); i++)
					merge.include(newParents.get(i));
				MergeResult mergeResult = merge.call();
				if (mergeResult.getMergeStatus().isSuccessful()) {
					CommitCommand commit = new Git(repo).commit();
					commit.setAuthor(commitToPick.getAuthorIdent());
					commit.setMessage(commitToPick.getFullMessage());
							+ commitToPick.getShortMessage());
					newHead = commit.call();
				} else {
					if (operation == Operation.BEGIN
							&& mergeResult.getMergeStatus() == MergeResult.MergeStatus.FAILED)
						return abort(RebaseResult.failed(mergeResult
								.getFailingPaths()));
					return stop(commitToPick, Status.STOPPED);

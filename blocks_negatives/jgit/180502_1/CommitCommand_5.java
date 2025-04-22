				RevCommit revCommit = rw.parseCommit(commitId);
				RefUpdate ru = repo.updateRef(Constants.HEAD);
				ru.setNewObjectId(commitId);
				if (!useDefaultReflogMessage) {
					ru.setRefLogMessage(reflogComment, false);
				} else {
					ru.setRefLogMessage(prefix + revCommit.getShortMessage(),
							false);
				}
				if (headId != null)
					ru.setExpectedOldObjectId(headId);
				else
					ru.setExpectedOldObjectId(ObjectId.zeroId());
				Result rc = ru.forceUpdate();
				switch (rc) {
				case NEW:
				case FORCED:
				case FAST_FORWARD: {
					setCallable(false);
					if (state == RepositoryState.MERGING_RESOLVED
							|| isMergeDuringRebase(state)) {
						repo.writeMergeCommitMsg(null);
						repo.writeMergeHeads(null);
					} else if (state == RepositoryState.CHERRY_PICKING_RESOLVED) {
						repo.writeMergeCommitMsg(null);
						repo.writeCherryPickHead(null);
					} else if (state == RepositoryState.REVERTING_RESOLVED) {
						repo.writeMergeCommitMsg(null);
						repo.writeRevertHead(null);
					}
					Hooks.postCommit(repo,
							hookOutRedirect.get(PostCommitHook.NAME),
							hookErrRedirect.get(PostCommitHook.NAME)).call();

					return revCommit;
				}
				case REJECTED:
				case LOCK_FAILURE:
					throw new ConcurrentRefUpdateException(
							JGitText.get().couldNotLockHEAD, ru.getRef(), rc);
				default:
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().updatingRefFailed, Constants.HEAD,
							commitId.toString(), rc));
				}

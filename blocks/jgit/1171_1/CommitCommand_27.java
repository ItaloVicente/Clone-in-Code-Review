						ru.setExpectedOldObjectId(headId);
						Result rc = ru.update();
						switch (rc) {
						case NEW:
						case FAST_FORWARD: {
							setCallable(false);
							File meta = repo.getDirectory();
							if (state == RepositoryState.MERGING_RESOLVED
									&& meta != null) {
								new File(meta
								new File(meta
							}
							return revCommit;
						}
						case REJECTED:
						case LOCK_FAILURE:
							throw new ConcurrentRefUpdateException(JGitText
									.get().couldNotLockHEAD
						default:
							throw new JGitInternalException(MessageFormat
									.format(JGitText.get().updatingRefFailed
											Constants.HEAD
											commitId.toString()
						}
					} finally {
						revWalk.release();

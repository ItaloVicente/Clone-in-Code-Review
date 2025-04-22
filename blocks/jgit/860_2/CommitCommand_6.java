					ru.setExpectedOldObjectId(headId);
					Result rc = ru.update();
					switch (rc) {
					case NEW:
					case FAST_FORWARD:
						setCallable(false);
						if (state == RepositoryState.MERGING_RESOLVED) {
							new File(repo.getDirectory()
									.delete();
							new File(repo.getDirectory()
									.delete();
						}
						return revCommit;
					case REJECTED:
					case LOCK_FAILURE:
						throw new ConcurrentRefUpdateException(
								JGitText.get().couldNotLockHEAD
								rc);
					default:
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().updatingRefFailed
								Constants.HEAD

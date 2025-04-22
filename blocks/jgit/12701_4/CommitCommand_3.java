						return revCommit;
					}
					case REJECTED:
					case LOCK_FAILURE:
						throw new ConcurrentRefUpdateException(
								JGitText.get().couldNotLockHEAD
								rc);
					default:
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().updatingRefFailed
								Constants.HEAD

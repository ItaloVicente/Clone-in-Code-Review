					return revCommit;
				}
				case REJECTED:
				case LOCK_FAILURE:
					throw new ConcurrentRefUpdateException(
							JGitText.get().couldNotLockHEAD
				default:
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().updatingRefFailed
							commitId.toString()

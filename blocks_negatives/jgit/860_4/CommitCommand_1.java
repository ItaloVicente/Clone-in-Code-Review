					return revCommit;
				case REJECTED:
				case LOCK_FAILURE:
					throw new ConcurrentRefUpdateException(
							JGitText.get().couldNotLockHEAD, ru.getRef(), rc);
				default:
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().updatingRefFailed
							, Constants.HEAD, commitId.toString(), rc));

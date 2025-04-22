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

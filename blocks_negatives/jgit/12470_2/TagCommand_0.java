				RevWalk revWalk = new RevWalk(repo);
				try {
					String refName = Constants.R_TAGS + newTag.getTag();
					RefUpdate tagRef = repo.updateRef(refName);
					tagRef.setNewObjectId(tagId);
					tagRef.setForceUpdate(forceUpdate);
					tagRef.setRefLogMessage("tagged " + name, false); //$NON-NLS-1$
					Result updateResult = tagRef.update(revWalk);
					switch (updateResult) {
					case NEW:
					case FORCED:
						return repo.getRef(refName);
					case LOCK_FAILURE:
						throw new ConcurrentRefUpdateException(
								JGitText.get().couldNotLockHEAD,
								tagRef.getRef(), updateResult);
					case REJECTED:
						throw new RefAlreadyExistsException(
								MessageFormat.format(
										JGitText.get().tagAlreadyExists,
										newTag.toString()));
					default:
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().updatingRefFailed, refName,
								newTag.toString(), updateResult));
					}

				} finally {
					revWalk.release();
				}

		} finally {
			revWalk.release();
		}
	}

	private Ref updateTagRef(ObjectId tagId
			String tagName
			ConcurrentRefUpdateException
		String refName = Constants.R_TAGS + tagName;
		RefUpdate tagRef = repo.updateRef(refName);
		tagRef.setNewObjectId(tagId);
		tagRef.setForceUpdate(forceUpdate);
		tagRef.setRefLogMessage("tagged " + name
		Result updateResult = tagRef.update(revWalk);
		switch (updateResult) {
		case NEW:
		case FORCED:
			return repo.getRef(refName);
		case LOCK_FAILURE:
			throw new ConcurrentRefUpdateException(
					JGitText.get().couldNotLockHEAD
					updateResult);
		case REJECTED:
			throw new RefAlreadyExistsException(MessageFormat.format(
					JGitText.get().tagAlreadyExists
		default:
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().updatingRefFailed
					updateResult));

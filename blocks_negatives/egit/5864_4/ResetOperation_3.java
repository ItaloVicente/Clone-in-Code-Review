	}

	private void mapObjects() throws TeamException {
		final ObjectId commitId;
		try {
			commitId = repository.resolve(refName);
		} catch (IOException e) {
			throw new TeamException(NLS.bind(
					CoreText.ResetOperation_lookingUpRef, refName), e);
		}
		RevWalk rw = new RevWalk(repository);
		try {
			commit = rw.parseCommit(commitId);
		} catch (IOException e) {
			throw new TeamException(NLS.bind(
					CoreText.ResetOperation_lookingUpCommit, commitId), e);
		} finally {
			rw.release();
		}
	}

	private void writeRef() throws TeamException {
		try {
			final RefUpdate ru = repository.updateRef(Constants.HEAD);
			ru.setNewObjectId(commit.getId());
			String name = refName;
				name = name.substring(11);
				name = name.substring(13);
			ru.setRefLogMessage(message, false);
			if (ru.forceUpdate() == RefUpdate.Result.LOCK_FAILURE)
				throw new TeamException(NLS.bind(
						CoreText.ResetOperation_cantUpdate, ru.getName()));
		} catch (IOException e) {
			throw new TeamException(NLS.bind(
					CoreText.ResetOperation_updatingFailed, Constants.HEAD), e);
		}
	}

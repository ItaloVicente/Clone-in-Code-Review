	public void execute(final ReceiveSession rs) {
		try {
			final RefUpdate ru = rs.getRepository().updateRef(getRefName());
			ru.setRefLogIdent(rs.getRefLogIdent());
			switch (getType()) {
			case DELETE:
				if (!ObjectId.zeroId().equals(getOldId())) {
					ru.setExpectedOldObjectId(getOldId());
				}
				ru.setForceUpdate(true);
				setResult(ru.delete(rs.getRevWalk()));
				break;

			case CREATE:
			case UPDATE:
			case UPDATE_NONFASTFORWARD:
				ru.setForceUpdate(rs.isAllowNonFastForwards());
				ru.setExpectedOldObjectId(getOldId());
				ru.setNewObjectId(getNewId());
				ru.setRefLogMessage("push"
				setResult(ru.update(rs.getRevWalk()));
				break;
			}
		} catch (IOException err) {
			setResult(Result.REJECTED_OTHER_REASON
					JGitText.get().lockError
		}
	}


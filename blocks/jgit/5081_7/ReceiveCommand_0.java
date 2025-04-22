	public void execute(final ReceivePack rp) {
		try {
			final RefUpdate ru = rp.getRepository().updateRef(getRefName());
			ru.setRefLogIdent(rp.getRefLogIdent());
			switch (getType()) {
			case DELETE:
				if (!ObjectId.zeroId().equals(getOldId())) {
					ru.setExpectedOldObjectId(getOldId());
				}
				ru.setForceUpdate(true);
				setResult(ru.delete(rp.getRevWalk()));
				break;

			case CREATE:
			case UPDATE:
			case UPDATE_NONFASTFORWARD:
				ru.setForceUpdate(rp.isAllowNonFastForwards());
				ru.setExpectedOldObjectId(getOldId());
				ru.setNewObjectId(getNewId());
				ru.setRefLogMessage("push"
				setResult(ru.update(rp.getRevWalk()));
				break;
			}
		} catch (IOException err) {
			setResult(Result.REJECTED_OTHER_REASON
					JGitText.get().lockError
		}
	}


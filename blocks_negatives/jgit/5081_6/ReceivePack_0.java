	private void execute(final ReceiveCommand cmd) {
		try {
			final RefUpdate ru = db.updateRef(cmd.getRefName());
			ru.setRefLogIdent(getRefLogIdent());
			switch (cmd.getType()) {
			case DELETE:
				if (!ObjectId.zeroId().equals(cmd.getOldId())) {
					ru.setExpectedOldObjectId(cmd.getOldId());
				}
				ru.setForceUpdate(true);
				status(cmd, ru.delete(walk));
				break;

			case CREATE:
			case UPDATE:
			case UPDATE_NONFASTFORWARD:
				ru.setForceUpdate(isAllowNonFastForwards());
				ru.setExpectedOldObjectId(cmd.getOldId());
				ru.setNewObjectId(cmd.getNewId());
				ru.setRefLogMessage("push", true);
				status(cmd, ru.update(walk));
				break;
			}
		} catch (IOException err) {
			cmd.setResult(Result.REJECTED_OTHER_REASON, MessageFormat.format(
					JGitText.get().lockError, err.getMessage()));
		}
	}

	private void status(final ReceiveCommand cmd, final RefUpdate.Result result) {
		switch (result) {
		case NOT_ATTEMPTED:
			cmd.setResult(Result.NOT_ATTEMPTED);
			break;

		case LOCK_FAILURE:
		case IO_FAILURE:
			cmd.setResult(Result.LOCK_FAILURE);
			break;

		case NO_CHANGE:
		case NEW:
		case FORCED:
		case FAST_FORWARD:
			cmd.setResult(Result.OK);
			break;

		case REJECTED:
			cmd.setResult(Result.REJECTED_NONFASTFORWARD);
			break;

		case REJECTED_CURRENT_BRANCH:
			cmd.setResult(Result.REJECTED_CURRENT_BRANCH);
			break;

		default:
			cmd.setResult(Result.REJECTED_OTHER_REASON, result.name());
			break;
		}
	}


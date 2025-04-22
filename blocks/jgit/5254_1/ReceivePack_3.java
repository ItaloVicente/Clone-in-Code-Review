
		BatchRefUpdate batch = db.getRefDatabase().newBatchUpdate();
		batch.setForceUpdate(isAllowNonFastForwards());
		batch.setRefLogIdent(getRefLogIdent());
		batch.setRefLogMessage("push"
		batch.addCommand(toApply);
		try {
			batch.execute(walk
		} catch (IOException err) {
			String msg = MessageFormat.format(JGitText.get().lockError
					.getMessage());
			for (ReceiveCommand cmd : commands) {
				if (cmd.getResult() == Result.NOT_ATTEMPTED)
					cmd.setResult(Result.REJECTED_OTHER_REASON
			}

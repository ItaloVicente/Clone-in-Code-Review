
		BatchRefUpdate batch = db.getRefDatabase().newBatchUpdate();
		batch.setAllowNonFastForwards(isAllowNonFastForwards());
		batch.setRefLogIdent(getRefLogIdent());
		batch.setRefLogMessage("push"
		batch.addCommand(toApply);
		try {
			batch.execute(walk
		} catch (IOException err) {
			for (ReceiveCommand cmd : toApply) {
				if (cmd.getResult() == Result.NOT_ATTEMPTED)
					cmd.reject(err);
			}

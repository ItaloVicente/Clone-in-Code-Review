	@Override
	protected void executeCommands() {
		if (getRepository().getRefDatabase() instanceof RefTreeDatabase) {
			List<ReceiveCommand> toApply = filterCommands(ReceiveCommand.Result.NOT_ATTEMPTED);
			if (toApply.isEmpty()) {
				return;
			}
			final BatchRefUpdate batch = ((RefTreeDatabase) getRepository().getRefDatabase()).getBootstrap()
					.newBatchUpdate();
			batch.setAllowNonFastForwards(true);
			batch.setAtomic(false);
			batch.setRefLogIdent(getRefLogIdent());
			batch.setRefLogMessage("push"
			batch.addCommand(toApply);
			try {
				batch.setPushCertificate(getPushCertificate());
				batch.execute(getRevWalk()
			} catch (IOException err) {
				for (ReceiveCommand cmd : toApply) {
					if (cmd.getResult() == ReceiveCommand.Result.NOT_ATTEMPTED) {
						cmd.setResult(ReceiveCommand.Result.REJECTED_OTHER_REASON
								MessageFormat.format(JGitText.get().lockError
					}
				}
			}
		} else {
			super.executeCommands();
		}
	}


		List<ReceiveCommand> toApply = filterCommands(Result.NOT_ATTEMPTED);
		ProgressMonitor updating = NullProgressMonitor.INSTANCE;
		if (sideBand) {
			SideBandProgressMonitor pm = new SideBandProgressMonitor(msgOut);
			pm.setDelayStart(250
			updating = pm;
		}
		updating.beginTask(JGitText.get().updatingReferences
		for (ReceiveCommand cmd : toApply) {
			updating.update(1);

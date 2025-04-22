	private boolean lockLooseRefs(List<ReceiveCommand> commands,
			Map<String, LockFile> locks) throws IOException {
		for (ReceiveCommand c : commands) {
			LockFile lock = new LockFile(refdb.fileFor(c.getRefName()));
			if (!lock.lock()) {
				lockFailure(c, commands);
				return false;

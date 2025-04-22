	@Nullable
	private Map<String
			throws IOException {
		ReceiveCommand failed = null;
		Map<String
		try {
			RETRY: for (int ms : refdb.getRetrySleepMs()) {
				failed = null;
				unlockAll(locks);
				locks.clear();
				RefDirectory.sleep(ms);

				for (ReceiveCommand c : commands) {
					String name = c.getRefName();
					LockFile lock = new LockFile(refdb.fileFor(name));
					if (locks.put(name
						throw new IOException(
								MessageFormat.format(JGitText.get().duplicateRef
					}
					if (!lock.lock()) {
						failed = c;
						continue RETRY;
					}
				}
				return locks;

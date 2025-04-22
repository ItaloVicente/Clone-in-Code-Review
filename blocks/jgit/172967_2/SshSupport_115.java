	private static int checkTimeout(String command
			throws CommandFailedException {
		long elapsed = System.nanoTime() - since;
		int newTimeout = timeout
				- (int) TimeUnit.NANOSECONDS.toSeconds(elapsed);
		if (newTimeout <= 0) {
			throw new CommandFailedException(0
					MessageFormat.format(JGitText.get().sshCommandTimeout
							command
		}
		return newTimeout;
	}

	public PersonIdent(String aName
		this(aName
	}

	private static long now() {
		Clock clock = SystemReader.getInstance().getClock();
		try (ProposedTimestamp t = clock.propose()) {
			long s = t.read(TimeUnit.SECONDS);
			t.blockUntil(5
			return TimeUnit.SECONDS.toMillis(s);
		} catch (InterruptedException | TimeoutException e) {
			throw new IllegalStateException(JGitText.get().timeIsUncertain
		}

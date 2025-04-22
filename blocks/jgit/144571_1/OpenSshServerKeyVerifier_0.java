
		private final JGitClientSession session;

		public AskUser(ClientSession clientSession) {
			session = (clientSession instanceof JGitClientSession)
					? (JGitClientSession) clientSession
					: null;

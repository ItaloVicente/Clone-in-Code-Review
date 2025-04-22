	private static class CrashingCommitMessageProvider
			implements ICommitMessageProvider {

		@Override
		public String getMessage(IResource[] resources) {
			throw new IllegalStateException(
					"CrashingCommitMessageProvider fails on purpose.");
		}

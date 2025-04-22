	private static class ChangeList extends CancelableFuture<Set<Change>> {

		private final Repository repository;

		private final String uriText;

		private ListRemoteOperation listOp;

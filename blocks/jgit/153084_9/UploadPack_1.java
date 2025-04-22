	private class PackSender<R extends FetchRequest> {
		protected final R req;

		protected final Collection<Ref> allTags;

		private final List<ObjectId> unshallowCommits;

		public PackSender(R req
				List<ObjectId> unshallowCommits) {
			this.req = req;
			this.allTags = allTags;
			this.unshallowCommits = unshallowCommits;

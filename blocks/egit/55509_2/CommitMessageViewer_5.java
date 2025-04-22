		setDocument(new CommitDocument(formatResult));
	}

	static class ObjectLink {
		private final RevCommit target;
		private final IRegion region;

		public ObjectLink(RevCommit target, IRegion region) {
			this.target = target;
			this.region = region;
		}

		public IRegion getRegion() {
			return region;
		}

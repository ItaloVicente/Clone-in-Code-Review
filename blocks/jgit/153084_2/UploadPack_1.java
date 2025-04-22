		private List<ObjectId> unshallowCommits;

		private List<ObjectId> deepenNots;

		public V2PackSender(FetchV2Request req
				List<ObjectId> unshallowCommits
				@Nullable Collection<Ref> allTags) {
			super(req);
			this.pckOut = pckOut;
			this.unshallowCommits = unshallowCommits;
			this.deepenNots = deepenNots;
			this.allTags = allTags;
		}

		@Override
		protected RevWalk preparePack(PackWriter pw
				throws IOException {
			if (req.getDepth() > 0 || req.getDeepenSince() != 0
					|| !deepenNots.isEmpty()) {
				rw = applyDepth(pw);

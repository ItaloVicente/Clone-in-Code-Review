		public V2PackSender(FetchV2Request req
				List<ObjectId> unshallowCommits
				Collection<Ref> allTags) {
			super(req
			this.pckOut = pckOut;
			this.deepenNots = deepenNots;
		}

		@Override
		protected RevWalk preparePack(PackWriter pw
				throws IOException {
			if (req.getDepth() > 0 || req.getDeepenSince() != 0
					|| !deepenNots.isEmpty()) {
				rw = applyDepth(pw);

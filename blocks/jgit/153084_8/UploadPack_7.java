			rw = super.preparePack(pw
			applyTags(pw
			return rw;
		}
	}

	private class V2PackSender extends PackSender<FetchV2Request> {
		private final PacketLineOut pckOut;

		private final List<ObjectId> deepenNots;

		public V2PackSender(FetchV2Request req
				List<ObjectId> unshallowCommits
				List<ObjectId> deepenNots) {
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

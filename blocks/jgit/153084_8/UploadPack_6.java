		protected DepthWalk.RevWalk applyDepth(PackWriter pw) {
			int walkDepth = req.getDepth() == 0 ? Integer.MAX_VALUE
					: req.getDepth() - 1;
			pw.setShallowPack(req.getDepth()

			DepthWalk.RevWalk dw = new DepthWalk.RevWalk(walk.getObjectReader()
					walkDepth);
			dw.setDeepenSince(req.getDeepenSince());
			dw.assumeShallow(req.getClientShallowCommits());
			return dw;
		}
	}

	private class V0PackSender extends PackSender<FetchV0Request> {
		public V0PackSender(FetchV0Request req
				List<ObjectId> unshallowCommits) {
			super(req
		}

		@Override
		protected RevWalk preparePack(PackWriter pw
				throws IOException {
			if (req.getDepth() > 0 || req.getDeepenSince() != 0) {
				rw = applyDepth(pw);

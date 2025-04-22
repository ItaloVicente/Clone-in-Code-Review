		private RevWalk applyDepth(PackWriter pw) {
			int walkDepth = req.getDepth() == 0 ? Integer.MAX_VALUE
					: req.getDepth() - 1;
			pw.setShallowPack(req.getDepth()

			DepthWalk.RevWalk dw = new DepthWalk.RevWalk(walk.getObjectReader()
					walkDepth);
			dw.setDeepenSince(req.getDeepenSince());
			dw.setDeepenNots(deepenNots);
			dw.assumeShallow(req.getClientShallowCommits());
			return dw;
		}

		@Override
		protected void writePack(PackWriter pw) throws IOException {
			if (pckOut.isUsingSideband() && cachedPackUriProvider != null
					&& !req.getPackfileUriProtocols().isEmpty()) {
				pw.setPackfileUriConfig(new PackWriter.PackfileUriConfig(pckOut
						req.getPackfileUriProtocols()
			} else {

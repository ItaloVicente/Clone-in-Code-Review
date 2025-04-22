			@Nullable Collection<Ref> allTags,
			List<ObjectId> unshallowCommits,
			List<ObjectId> deepenNots,
			PacketLineOut pckOut) throws IOException {
		ProgressMonitor pm = NullProgressMonitor.INSTANCE;
		OutputStream packOut = rawOut;

		if (sideband) {
			int bufsz = SideBandOutputStream.SMALL_BUF;
			if (req.getClientCapabilities().contains(OPTION_SIDE_BAND_64K))
				bufsz = SideBandOutputStream.MAX_BUF;

			packOut = new SideBandOutputStream(SideBandOutputStream.CH_DATA,
					bufsz, rawOut);
			if (!req.getClientCapabilities().contains(OPTION_NO_PROGRESS)) {
				msgOut = new SideBandOutputStream(
						SideBandOutputStream.CH_PROGRESS, bufsz, rawOut);
				pm = new SideBandProgressMonitor(msgOut);
			}
		}


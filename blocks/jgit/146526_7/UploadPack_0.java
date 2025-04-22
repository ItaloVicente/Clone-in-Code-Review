			int bufsz = SideBandOutputStream.SMALL_BUF;
			if (req.getClientCapabilities().contains(OPTION_SIDE_BAND_64K)) {
				bufsz = SideBandOutputStream.MAX_BUF;
			}
			OutputStream packOut = new SideBandOutputStream(
					SideBandOutputStream.CH_DATA

			ProgressMonitor pm = NullProgressMonitor.INSTANCE;
			if (!req.getClientCapabilities().contains(OPTION_NO_PROGRESS)) {
				msgOut = new SideBandOutputStream(
						SideBandOutputStream.CH_PROGRESS
				pm = new SideBandProgressMonitor(msgOut);

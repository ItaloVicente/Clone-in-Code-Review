	SideBandProgressMonitor(final PacketLineOut pckOut) {
		final int bufsz = SideBandOutputStream.SMALL_BUF
				- SideBandOutputStream.HDR_SIZE;
		out = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(
				new SideBandOutputStream(SideBandOutputStream.CH_PROGRESS,
						pckOut), bufsz), Constants.CHARSET));

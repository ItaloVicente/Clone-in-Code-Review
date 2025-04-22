	SideBandInputStream(final InputStream in
			final Writer messageStream) {
		rawIn = in;
		pckIn = new PacketLineIn(rawIn);
		monitor = progress;
		messages = messageStream;

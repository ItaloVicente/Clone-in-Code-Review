	private void readAdvertisedRefsImpl() throws IOException {
		final LinkedHashMap<String, Ref> avail = new LinkedHashMap<>();
		for (;;) {
			String line;

			try {
				line = pckIn.readString();
			} catch (EOFException eof) {
				if (avail.isEmpty())
					throw noRepository();
				throw eof;
			}
			if (PacketLineIn.isEnd(line))
				break;

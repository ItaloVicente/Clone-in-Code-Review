	private boolean consumeServerOptions(ServerOptions serverOptions)
			throws IOException {
		String line = pckIn.readString();
		while (line != PacketLineIn.DELIM && line != PacketLineIn.END) {
			serverOptions.add(line);
			line = pckIn.readString();
		}
		return line != PacketLineIn.END;
	}


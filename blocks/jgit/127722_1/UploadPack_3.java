	private boolean consumeCmdCapabilites(CommandCapabilities cmdCapabilities)
			throws IOException {
		String line = pckIn.readString();
		while (line != PacketLineIn.DELIM && line != PacketLineIn.END) {
			cmdCapabilities.addCmdCapability(line);
			line = pckIn.readString();
		}
		return line != PacketLineIn.END;
	}


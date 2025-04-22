		CommandCapabilities caps = new CommandCapabilities();
		boolean moreInput = consumeCmdCapabilites(caps);

		String line;
		while (moreInput && (line = pckIn.readString()) != PacketLineIn.END) {
				builder.setPeel(true);
				builder.setSymrefs(true);
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine

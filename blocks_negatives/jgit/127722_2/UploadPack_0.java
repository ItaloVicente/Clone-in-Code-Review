		String line = pckIn.readString();
		if (line == PacketLineIn.DELIM) {
			while ((line = pckIn.readString()) != PacketLineIn.END) {
					builder.setPeel(true);
					builder.setSymrefs(true);
				} else {
					throw new PackProtocolException(MessageFormat
							.format(JGitText.get().unexpectedPacketLine, line));
				}

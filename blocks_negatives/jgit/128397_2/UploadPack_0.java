		String line;

		if ((line = pckIn.readString()) != PacketLineIn.DELIM) {
			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().unexpectedPacketLine, line));
		}

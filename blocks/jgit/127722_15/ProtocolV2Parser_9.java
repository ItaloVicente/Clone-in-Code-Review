		if (line == PacketLineIn.END) {
			return reqBuilder.build();
		}

		if (line != PacketLineIn.DELIM) {
			throw new PackProtocolException(
					MessageFormat.format(JGitText.get().unexpectedPacketLine
							line));


		String line = consumeCapabilities(pckIn
				serverOption -> builder.addServerOption(serverOption)
				agent -> builder.setAgent(agent));

		if (line == PacketLineIn.END) {
			return builder.build();
		}

		while ((line = pckIn.readString()) != PacketLineIn.END) {
				builder.setPeel(true);
				builder.setSymrefs(true);
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine

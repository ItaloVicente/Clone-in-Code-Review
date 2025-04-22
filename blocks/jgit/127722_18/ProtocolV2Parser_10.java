
		String line = consumeCapabilities(pckIn
				serverOption -> builder.addServerOption(serverOption)
				agent -> builder.setAgent(agent));

		if (line == PacketLineIn.END) {
			return builder.build();
		}

		if (line != PacketLineIn.DELIM) {

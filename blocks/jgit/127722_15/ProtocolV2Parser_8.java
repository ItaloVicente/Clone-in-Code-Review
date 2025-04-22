	private static String consumeCapabilities(PacketLineIn pckIn
			Consumer<String> serverOptionConsumer
			Consumer<String> agentConsumer) throws IOException {

		String serverOptionPrefix = OPTION_SERVER_OPTION + '=';
		String agentPrefix = OPTION_AGENT + '=';

		String line = pckIn.readString();
		while (line != PacketLineIn.DELIM && line != PacketLineIn.END) {
			if (line.startsWith(serverOptionPrefix)) {
				serverOptionConsumer
						.accept(line.substring(serverOptionPrefix.length()));
			} else if (line.startsWith(agentPrefix)) {
				agentConsumer.accept(line.substring(agentPrefix.length()));
			} else {
			}
			line = pckIn.readString();
		}

		return line;
	}


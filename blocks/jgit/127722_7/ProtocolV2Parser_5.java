
		while (line != PacketLineIn.DELIM && line != PacketLineIn.END) {
				builder.addServerOption(line.substring(14));
				builder.setAgent(line.substring(6));
			}
			line = pckIn.readString();
		}

		if (line == PacketLineIn.END) {
			return builder.build();
		}

		while ((line = pckIn.readString()) != PacketLineIn.END) {
				builder.setPeel(true);
				builder.setSymrefs(true);
			} else {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().unexpectedPacketLine

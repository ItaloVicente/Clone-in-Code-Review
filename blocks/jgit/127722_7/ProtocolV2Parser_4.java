		String line = pckIn.readString();
		while (line != PacketLineIn.DELIM && line != PacketLineIn.END) {
				reqBuilder.addServerOption(line.substring(14));
				reqBuilder.addAgent(line.substring(6));
			}
			line = pckIn.readString();
		}

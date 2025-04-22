	private static ByteArrayInputStream linesAsInputStream(String... inputLines)
			throws IOException {
		try (ByteArrayOutputStream send = new ByteArrayOutputStream()) {
			PacketLineOut pckOut = new PacketLineOut(send);
			for (String line : inputLines) {
				if (line == PacketLineIn.END) {
					pckOut.end();
				} else if (line == PacketLineIn.DELIM) {
					pckOut.writeDelim();
				} else {
					pckOut.writeString(line);
				}
			}
			return new ByteArrayInputStream(send.toByteArray());
		}
	}


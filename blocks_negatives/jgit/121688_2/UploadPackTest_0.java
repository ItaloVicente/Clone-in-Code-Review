	private static ByteArrayInputStream send(String... lines) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PacketLineOut pckOut = new PacketLineOut(os);
		for (String line : lines) {
			if (line == PacketLineIn.END) {
				pckOut.end();
			} else if (line == PacketLineIn.DELIM) {
				pckOut.writeDelim();
			} else {
				pckOut.writeString(line);
			}
		}
		byte[] a = os.toByteArray();
		return new ByteArrayInputStream(a);
	}


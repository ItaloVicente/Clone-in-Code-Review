		try (OutputStream os = c.getOutputStream()) {
			PacketLineOut pckOut = new PacketLineOut(os);
			pckOut.writeString("command=ls-refs");
			pckOut.writeDelim();
			pckOut.end();
		}

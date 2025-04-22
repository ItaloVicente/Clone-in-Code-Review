	private boolean serveOneCommandV2() throws IOException {
		String command;
		try {
			command = pckIn.readString();
		} catch (EOFException eof) {
			return true;
		}
		if (command == PacketLineIn.END) {
			return true;
		}
		throw new PackProtocolException("unknown command " + command);
	}

	private void serviceV2() throws IOException {
		if (biDirectionalPipe) {
			for (String s : v2CapabilityAdvertisement) {
				pckOut.writeString(s + "\n");
			}
			pckOut.end();

			while (!serveOneCommandV2()) {
			}
			return;
		}

		try {
			serveOneCommandV2();
		} finally {
			while (0 < rawIn.skip(2048) || 0 <= rawIn.read()) {
			}
			rawOut.stopBuffering();
		}
	}


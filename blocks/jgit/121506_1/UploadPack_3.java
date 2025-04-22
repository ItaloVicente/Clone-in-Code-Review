	private void serviceV2() throws IOException {
		if (biDirectionalPipe) {
			for (String s : v2CapabilityAdvertisement) {
				pckOut.writeString(s + "\n");
			}
			pckOut.end();
		}

		try {
			while (true) {
				String command;
				try {
					command = pckIn.readString();
				} catch (EOFException eof) {
					break;
				}
				if (command == PacketLineIn.END) {
				} else {
					throw new PackProtocolException("unknown command " + command);
				}
			}
		} finally {
			rawOut.stopBuffering();
		}
	}


	private void lsRefsV2() throws IOException {
		PacketLineOutRefAdvertiser adv = new PacketLineOutRefAdvertiser(pckOut);
		Map<String
		String line;

		adv.setUseProtocolV2(true);

		line = pckIn.readString();

		if (line == PacketLineIn.DELIM) {
			while ((line = pckIn.readString()) != PacketLineIn.END) {
				if (line.equals("peel")) {
					adv.setDerefTags(true);
				} else if (line.equals("symrefs")) {
					findSymrefs(adv
				} else {
					throw new PackProtocolException("unexpected " + line);
				}
			}
		} else if (line != PacketLineIn.END) {
			throw new PackProtocolException("unexpected " + line);
		}

		adv.send(refs);
		adv.end();
	}

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
				} else if (command.equals("command=" + CAPABILITY_LS_REFS)) {
					lsRefsV2();
				} else {
					throw new PackProtocolException("unknown command " + command);
				}
			}
		} finally {
			rawOut.stopBuffering();
		}
	}


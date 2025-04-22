	private void lsRefsV2() throws IOException {
		PacketLineOutRefAdvertiser adv = new PacketLineOutRefAdvertiser(pckOut);
		Map<String
		String line;

		adv.setUseProtocolV2(true);

		while ((line = pckIn.readString()) != PacketLineIn.END) {
			if (line.equals("peel")) {
				adv.setDerefTags(true);
			} else if (line.equals("symrefs")) {
				findSymrefs(adv
			}
		}
		adv.send(refs);
		adv.end();
	}

	private void serviceV2() throws IOException {
		if (biDirectionalPipe) {
			for (String s : v2CapabilityAdvertisement) {
				pckOut.writeString(s);
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
				while (pckIn.readString() != PacketLineIn.DELIM) {
				}
				if (command.equals("command=ls-refs")) {
					lsRefsV2();
				} else {
					throw new PackProtocolException("unknown command " + command);
				}
			}
		} finally {
			rawOut.stopBuffering();
		}
	}


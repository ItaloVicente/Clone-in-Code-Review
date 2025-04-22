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


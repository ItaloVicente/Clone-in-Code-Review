		PacketLineOutRefAdvertiser adv = new PacketLineOutRefAdvertiser(pckOut);
		String line;
		ArrayList<String> refPrefixes = new ArrayList<>();
		boolean needToFindSymrefs = false;

		adv.setUseProtocolV2(true);

		line = pckIn.readString();


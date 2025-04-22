		rawOut.stopBuffering();
		PacketLineOutRefAdvertiser adv = new PacketLineOutRefAdvertiser(pckOut);
		adv.setUseProtocolV2(true);
		if (req.getPeel()) {
			adv.setDerefTags(true);
		}


		sideBand = enabledCapablities.contains(CAPABILITY_SIDE_BAND_64K);
		if (sideBand) {
			OutputStream out = rawOut;

			rawOut = new SideBandOutputStream(CH_DATA
			pckOut = new PacketLineOut(rawOut);
			msgs = new OutputStreamWriter(new SideBandOutputStream(CH_PROGRESS
					MAX_BUF
		}

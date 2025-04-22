	private void fetchV2() throws IOException {
		options = new HashSet<>();

		options.add(OPTION_SIDE_BAND_64K);

		if (requestValidator instanceof ReachableCommitRequestValidator ||
				requestValidator instanceof AdvertisedRequestValidator)
			advertised = refIdSet(getAdvertisedOrDefaultRefs().values());
		else
			advertised = Collections.emptySet();

		String line;
		List<ObjectId> peerHas = new ArrayList<>();
		boolean doneReceived = false;

		if ((line = pckIn.readString()) != PacketLineIn.DELIM) {
			throw new PackProtocolException("unexpected " + line);
		}

		while ((line = pckIn.readString()) != PacketLineIn.END) {
			if (line.startsWith("want ")) {
				wantIds.add(ObjectId.fromString(line.substring(5)));
			} else if (line.startsWith("have ")) {
				peerHas.add(ObjectId.fromString(line.substring(5)));
			} else if (line.equals("done")) {
				doneReceived = true;
			}
		}

		boolean sectionSent = false;
		if (doneReceived) {
			processHaveLines(peerHas
		} else {
			pckOut.writeString("acknowledgments\n");
			for (ObjectId id : peerHas) {
				if (walk.getObjectReader().has(id)) {
					pckOut.writeString("ACK " + id.getName() + "\n");
				}
			}
			processHaveLines(peerHas
			if (okToGiveUp()) {
				pckOut.writeString("ready\n");
			} else if (commonBase.isEmpty()) {
				pckOut.writeString("NAK\n");
			}
			sectionSent = true;
		}
		if (doneReceived || okToGiveUp()) {
			if (sectionSent)
				pckOut.writeDelim();
			pckOut.writeString("packfile\n");
			sendPack(new PackStatistics.Accumulator());
		}
		pckOut.end();
	}


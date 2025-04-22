	private void fetchV2() throws IOException {
		options = new HashSet<>();

		options.add(OPTION_SIDE_BAND_64K);

		setRequestPolicy(RequestPolicy.ANY);
		advertised = Collections.<ObjectId>emptySet();

		String line;
		List<ObjectId> peerHas = new ArrayList<>();
		boolean doneReceived = false;
		while ((line = pckIn.readString()) != PacketLineIn.END) {
			if (line.startsWith("want ")) {
				wantIds.add(ObjectId.fromString(line.substring(5)));
			} else if (line.startsWith("have ")) {
				peerHas.add(ObjectId.fromString(line.substring(5)));
			} else if (line.equals("done")) {
				doneReceived = true;
			}
		}
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
				pckOut.writeDelim();
			} else if (commonBase.isEmpty()) {
				pckOut.writeString("NAK\n");
			}
		}
		if (doneReceived || okToGiveUp()) {
			pckOut.writeString("packfile\n");
			sendPack(new PackStatistics.Accumulator());
		}
		pckOut.end();
	}


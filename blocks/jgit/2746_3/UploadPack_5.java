		boolean sentReady = false;
		if (0 < missCnt) {
			for (int i = peerHas.size() - 1; i >= 0; i--) {
				ObjectId id = peerHas.get(i);
				if (walk.lookupOrNull(id) == null) {
					didOkToGiveUp = true;
					if (okToGiveUp()) {
						switch (multiAck) {
						case OFF:
							break;
						case CONTINUE:
							pckOut.writeString("ACK " + id.name() + " continue\n");
							break;
						case DETAILED:
							pckOut.writeString("ACK " + id.name() + " ready\n");
							sentReady = true;
							break;
						}

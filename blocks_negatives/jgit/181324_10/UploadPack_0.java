		boolean didOkToGiveUp = false;
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
							break;
						case DETAILED:
							sentReady = true;
							break;

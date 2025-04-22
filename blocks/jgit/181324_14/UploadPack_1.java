		if (option != Option.WAIT_FOR_DONE) {
			sentReady = shouldGiveUp(peerHas
		}

		preUploadHook.onEndNegotiateRound(this
		peerHas.clear();
		return last;
	}

	private boolean shouldGiveUp(List<ObjectId> peerHas
			throws IOException {
		boolean sentReady = false;

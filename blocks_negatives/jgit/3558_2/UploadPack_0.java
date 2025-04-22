		recvWants();
		if (wantIds.isEmpty()) {
			preUploadHook.onBeginNegotiateRound(this, wantIds, 0);
			preUploadHook.onEndNegotiateRound(this, wantIds, 0, 0, false);
			return;
		}

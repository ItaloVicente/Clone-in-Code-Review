	@Override
	protected void finishedPayload(byte[] pl) throws IOException {
		if (errorCode == SASL_CONTINUE) {
			getCallback().receivedStatus(new OperationStatus(true,
					new String(pl)));
			transitionState(OperationState.COMPLETE);
		} else if(errorCode == 0) {
			getCallback().receivedStatus(new OperationStatus(true, ""));
			transitionState(OperationState.COMPLETE);
		} else {
			super.finishedPayload(pl);
		}
	}

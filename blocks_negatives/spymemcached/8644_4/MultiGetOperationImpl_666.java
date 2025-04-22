	@Override
	protected void finishedPayload(byte[] pl) throws IOException {
		if(responseOpaque == terminalOpaque) {
			getCallback().receivedStatus(STATUS_OK);
			transitionState(OperationState.COMPLETE);
		} else if(errorCode != 0) {
			getLogger().warn("Error on key %s:  %s (%d)",
				keys.get(responseOpaque), new String(pl), errorCode);
		} else {
			final int flags=decodeInt(pl, 0);
			final byte[] data=new byte[pl.length - EXTRA_HDR_LEN];
			System.arraycopy(pl, EXTRA_HDR_LEN, data,
					0, pl.length-EXTRA_HDR_LEN);
			Callback cb=(Callback)getCallback();
			cb.gotData(keys.get(responseOpaque), flags, data);
		}
		resetInput();
	}

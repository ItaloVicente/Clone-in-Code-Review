	@Override
	protected void decodePayload(byte[] pl) {
		final int flags=decodeInt(pl, 0);
		final byte[] data=new byte[pl.length - EXTRA_HDR_LEN];
		System.arraycopy(pl, EXTRA_HDR_LEN, data, 0, pl.length-EXTRA_HDR_LEN);
		GetAndTouchOperation.Callback gcb=(GetAndTouchOperation.Callback)getCallback();
		gcb.gotData(key, flags, responseCas, data);
		getCallback().receivedStatus(STATUS_OK);
	}

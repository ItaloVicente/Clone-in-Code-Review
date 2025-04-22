	private void writeBaseFirst(final ObjectToPack otp) throws IOException {
		ObjectToPack baseInPack = otp.getDeltaBase();
		if (baseInPack != null) {
			if (!baseInPack.isWritten()) {
				if (baseInPack.wantWrite()) {
					reuseDeltas = false;
					redoSearchForReuse(otp);
					reuseDeltas = true;
				} else {
					writeObject(baseInPack);
				}

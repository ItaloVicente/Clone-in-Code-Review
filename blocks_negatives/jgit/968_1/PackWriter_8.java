	private void writeObject(final LocalObjectToPack otp) throws IOException {
		otp.markWantWrite();
		if (otp.isDeltaRepresentation()) {
			LocalObjectToPack deltaBase = (LocalObjectToPack)otp.getDeltaBase();
			assert deltaBase != null || thin;
			if (deltaBase != null && !deltaBase.isWritten()) {
				if (deltaBase.wantWrite()) {
					otp.clearReuseAsIs();
				} else {
					writeObject(deltaBase);
				}
			}
		}

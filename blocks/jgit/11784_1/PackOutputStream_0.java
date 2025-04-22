		ObjectToPack b = otp.getDeltaBase();
		if (b != null && (b.isWritten() & ofsDelta)) {
			int n = objectHeader(rawLength
			n = ofsDelta(count - b.getOffset()
			write(headerBuffer
		} else if (otp.isDeltaRepresentation()) {
			int n = objectHeader(rawLength

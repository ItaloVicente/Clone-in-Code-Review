		int signatureStart = getSignatureStart();
		if (signatureStart >= msgB && msgE > signatureStart) {
			msgE = signatureStart;
			if (msgE > msgB) {
				msgE--;
			}
			if (msgB == msgE) {
			}
		}

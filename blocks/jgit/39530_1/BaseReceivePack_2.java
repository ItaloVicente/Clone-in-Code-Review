
	protected String checkNonce(String header) {
		String nonce = findHeader(header

		if (nonce.isEmpty())
			return NONCE_MISSING;
		else if (pushCertNonce.isEmpty())
			return NONCE_UNSOLICITED;
		else if (nonce.equals(pushCertNonce))
			return NONCE_OK;


		int idxNonce = nonce.indexOf('-');
		int idxPushCert = pushCertNonce.indexOf('-');
		if (idxNonce == -1 || idxPushCert == -1)
			return NONCE_BAD;

		long signedStamp = 0;
		long advertisedStamp = 0;
		try {
			signedStamp = Integer.parseInt(nonce.substring(0
			advertisedStamp = Integer.parseInt(
					pushCertNonce.substring(0
		} catch (Exception e) {
			return NONCE_BAD;
		}

		String expect = preparePushCertNonce("TODO_dir"

		if (!expect.equals(nonce))
			return NONCE_BAD;

		long nonceStampSlop = Math.abs(advertisedStamp - signedStamp);

		if (certNonceSlopLimit != 0
				&& nonceStampSlop <= certNonceSlopLimit) {
			return NONCE_OK;
		} else {
			return NONCE_SLOP;
		}
	}



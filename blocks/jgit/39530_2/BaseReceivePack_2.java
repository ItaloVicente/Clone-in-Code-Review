	protected String preparePushCertNonce(String path
		throws IllegalStateException
	{

		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes

		Mac mac = null;
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
		byte[] rawHmac = mac.doFinal(certNonceSeed.getBytes());
		String hexString = String.format("%20X"
		return hexString;
	}


	protected String checkNonce(String header) {
		if (pushedCertNonce.isEmpty())
			return NONCE_MISSING;
		else if (pushCertNonce.isEmpty())
			return NONCE_UNSOLICITED;
		else if (pushedCertNonce.equals(pushCertNonce))
			return NONCE_OK;


		int idxNonce = pushedCertNonce.indexOf('-');
		int idxPushCert = pushCertNonce.indexOf('-');
		if (idxNonce == -1 || idxPushCert == -1)
			return NONCE_BAD;

		long signedStamp = 0;
		long advertisedStamp = 0;
		try {
			signedStamp = Integer.parseInt(pushedCertNonce.substring(0
			advertisedStamp = Integer.parseInt(
					pushCertNonce.substring(0
		} catch (Exception e) {
			return NONCE_BAD;
		}

		String expect = preparePushCertNonce(
				db.getDirectory().getPath()
				signedStamp);

		if (!expect.equals(pushedCertNonce))
			return NONCE_BAD;

		long nonceStampSlop = Math.abs(advertisedStamp - signedStamp);

		if (certNonceSlopLimit != 0
				&& nonceStampSlop <= certNonceSlopLimit) {
			return NONCE_OK;
		} else {
			return NONCE_SLOP;
		}
	}





	static PGPPublicKey findPublicKey(String fingerprint
			throws IOException
		PGPPublicKey result = findPublicKeyInPubring(USER_PGP_PUBRING_FILE
				fingerprint
		if (result == null && exists(USER_KEYBOX_PATH)) {
			try {
				result = findPublicKeyInKeyBox(USER_KEYBOX_PATH
						keySpec);
			} catch (NoSuchAlgorithmException | NoSuchProviderException
					| IOException | NoOpenPgpKeyException e) {
				log.error(e.getMessage()
			}
		}
		return result;
	}

	private static PGPPublicKey findPublicKeyByKeyId(KeyBlob keyBlob
			String keyId)

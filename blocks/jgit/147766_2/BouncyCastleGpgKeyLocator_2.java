	private PGPPublicKey findPublicKeyInPubring(Path pubringFile)
			throws IOException
		try (InputStream in = newInputStream(pubringFile)) {
			PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(
					new BufferedInputStream(in)
					new JcaKeyFingerprintCalculator());

			String keyId = signingKey.toLowerCase(Locale.ROOT);
			Iterator<PGPPublicKeyRing> keyrings = pgpPub.getKeyRings();
			while (keyrings.hasNext()) {
				PGPPublicKeyRing keyRing = keyrings.next();
				Iterator<PGPPublicKey> keys = keyRing.getPublicKeys();
				while (keys.hasNext()) {
					PGPPublicKey key = keys.next();
					String fingerprint = Hex.toHexString(key.getFingerprint())
							.toLowerCase(Locale.ROOT);
					if (fingerprint.endsWith(keyId)) {
						return key;
					}
					Iterator<String> userIDs = key.getUserIDs();
					while (userIDs.hasNext()) {
						String userId = userIDs.next();
						if (containsSigningKey(userId)) {
							return key;
						}
					}
				}
			}
		}
		return null;
	}


	private KeyPair loadKey(SessionContext session
			Path path
			throws IOException
		try (InputStream stream = Files.newInputStream(path)) {
			Iterable<KeyPair> ids = SecurityUtils.loadKeyPairIdentities(session
					resource
			if (ids == null) {
				throw new InvalidKeyException(
						format(SshdText.get().identityFileNoKey
			}
			Iterator<KeyPair> keys = ids.iterator();
			if (!keys.hasNext()) {
				throw new InvalidKeyException(format(
						SshdText.get().identityFileUnsupportedFormat
			}
			KeyPair result = keys.next();
			if (keys.hasNext()) {
				log.warn(format(SshdText.get().identityFileMultipleKeys
				keys.forEachRemaining(k -> {
					PrivateKey pk = k.getPrivate();
					if (pk != null) {
						try {
							pk.destroy();
						} catch (DestroyFailedException e) {
						}
					}
				});
			}
			return result;
		}
	}


	private static PublicKey readPublicKey(Path key)
			throws IOException
		return AuthorizedKeyEntry.readAuthorizedKeys(key).get(0)
				.resolvePublicKey(null
	}

	private static KeyPair readKeyPair(byte[] keyMaterial)
			throws IOException
		try (ByteArrayInputStream in = new ByteArrayInputStream(keyMaterial)) {
			return SecurityUtils.loadKeyPairIdentities(null
					.iterator().next();
		}
	}


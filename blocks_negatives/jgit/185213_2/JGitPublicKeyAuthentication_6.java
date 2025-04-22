		if (currentAlgorithm == null) {
			try {
				if (keys == null || !keys.hasNext()) {
					if (log.isDebugEnabled()) {
						log.debug(
								"sendAuthDataRequest({})[{}] no more keys to send", //$NON-NLS-1$
								session, service);
					}
					current = null;
					return false;
				}
				current = keys.next();
				currentAlgorithms.clear();
				chosenAlgorithm = null;
				throw new RuntimeSshException(e);
			}
		}
		PublicKey key;
		try {
			key = current.getPublicKey();
			throw new RuntimeSshException(e);
		}
		if (currentAlgorithm == null) {
			String keyType = KeyUtils.getKeyType(key);
			Set<String> aliases = new HashSet<>(
					KeyUtils.getAllEquivalentKeyTypes(keyType));
			aliases.add(keyType);
			List<NamedFactory<Signature>> existingFactories;
			if (current instanceof SignatureFactoriesHolder) {
				existingFactories = ((SignatureFactoriesHolder) current)
						.getSignatureFactories();
			} else {
				existingFactories = getSignatureFactories();
			}
			if (existingFactories != null) {

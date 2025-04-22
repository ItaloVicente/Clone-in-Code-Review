			log.warn(format(SshdText.get().configNoKnownAlgorithms
					PUBKEY_ACCEPTED_ALGORITHMS
		}
		List<NamedFactory<Signature>> localFactories = getSignatureFactories();
		if (localFactories == null || localFactories.isEmpty()) {
			setSignatureFactoriesNames(session.getSignatureFactoriesNames());
